package me.oussamamessaoudi.openingaccount.application.service;

import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;
import java.util.Optional;
import java.util.function.Function;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.oussamamessaoudi.openingaccount.application.domain.entity.Account;
import me.oussamamessaoudi.openingaccount.application.domain.entity.Transaction;
import me.oussamamessaoudi.openingaccount.application.domain.model.NewAccountCreatedDTO;
import me.oussamamessaoudi.openingaccount.application.domain.model.NewAccountCreationDTO;
import me.oussamamessaoudi.openingaccount.application.domain.repository.AccountRepository;
import me.oussamamessaoudi.openingaccount.application.exception.CodeError;
import me.oussamamessaoudi.openingaccount.application.exception.OpeningAccountException;
import me.oussamamessaoudi.openingaccount.application.helpers.FaultTolerantFunction;
import me.oussamamessaoudi.openingaccount.application.mapper.AccountMapper;
import org.springframework.http.HttpStatus;

@Slf4j
@AllArgsConstructor
public class AccountService {

  private AccountRepository accountRepository;

  private CustomerService customerService;
  private TransactionService transactionService;

  private AccountMapper accountMapper;

  @Transactional(TxType.NEVER)
  public NewAccountCreatedDTO createAccountWithDeposit(NewAccountCreationDTO newAccountCreation) {
    return createAccount(newAccountCreation)
        .map(
            FaultTolerantFunction.of(
                (Account acc) ->
                    transactionService.createTransaction(
                        Transaction.builder()
                            .account(acc)
                            .amount(newAccountCreation.getInitialCredit())
                            .label("Initial deposit")
                            .build()),
                Function.identity()))
        .map(accountMapper::mapAccountToNewAccountCreatedDTO)
        .orElseThrow(
            () -> OpeningAccountException.builder().codeError(CodeError.INTERNAL_ERROR).build());
  }

  @Transactional(TxType.REQUIRES_NEW)
  public Optional<Account> createAccount(NewAccountCreationDTO newAccountCreation) {
    return Optional.of(newAccountCreation)
        .map(NewAccountCreationDTO::getCustomerId)
        .filter(customerService::checkCustomerExistence)
        .or(
            () -> {
              throw OpeningAccountException.builder()
                  .codeError(CodeError.CUSTOMER_NOT_FOUND)
                  .httpStatus(HttpStatus.NOT_FOUND.value())
                  .build()
                  .addParam(newAccountCreation.getCustomerId());
            })
        .map(customerId -> Account.builder().customerId(customerId).build())
        .map(FaultTolerantFunction.of(accountRepository::save))
        .or(
            () -> {
              throw OpeningAccountException.builder()
                  .codeError(CodeError.ERROR_CREATING_ACCOUNT)
                  .build()
                  .addParam(newAccountCreation.getCustomerId());
            });
  }
}
