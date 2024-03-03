package me.oussamamessaoudi.openingaccount.application.service;

import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.oussamamessaoudi.openingaccount.application.domain.entity.Account;
import me.oussamamessaoudi.openingaccount.application.domain.entity.Transaction;
import me.oussamamessaoudi.openingaccount.application.domain.model.NewAccountCreatedDTO;
import me.oussamamessaoudi.openingaccount.application.domain.model.NewAccountCreationDTO;
import me.oussamamessaoudi.openingaccount.application.domain.repository.AccountRepository;
import me.oussamamessaoudi.openingaccount.application.domain.repository.CustomerRepository;
import me.oussamamessaoudi.openingaccount.application.exception.CodeError;
import me.oussamamessaoudi.openingaccount.application.exception.ExceptionOpeningAccount;
import me.oussamamessaoudi.openingaccount.application.mapper.AccountMapper;
import org.springframework.http.HttpStatus;

@Slf4j
@AllArgsConstructor
public class AccountService {

  private CustomerRepository customerRepository;
  private AccountRepository accountRepository;

  private TransactionService transactionService;

  private AccountMapper accountMapper;

  public NewAccountCreatedDTO createAccountWithDeposit(NewAccountCreationDTO newAccountCreation) {
    return createAccount(newAccountCreation)
        .map(
            account -> {
              try {
                return transactionService.createTransaction(
                    Transaction.builder()
                        .account(account)
                        .amount(newAccountCreation.getInitialCredit())
                        .label("Initial deposit")
                        .build());
              } catch (Exception exceptionOpeningAccount) {
                log.error(exceptionOpeningAccount.toString());
                return account;
              }
            })
        .map(accountMapper::mapAccountToNewAccountCreatedDTO)
        .orElseThrow(
            () -> ExceptionOpeningAccount.builder().codeError(CodeError.INTERNAL_ERROR).build());
  }

  @Transactional
  public Optional<Account> createAccount(NewAccountCreationDTO newAccountCreation) {
    return customerRepository
        .getByCustomerId(newAccountCreation.getCustomerId())
        .or(
            () -> {
              throw ExceptionOpeningAccount.builder()
                  .codeError(CodeError.CUSTOMER_NOT_FOUND)
                  .httpStatus(HttpStatus.NOT_FOUND.value())
                  .build()
                  .addParam(newAccountCreation.getCustomerId());
            })
        .map(customer -> accountRepository.save(Account.builder().customer(customer).build()));
  }
}
