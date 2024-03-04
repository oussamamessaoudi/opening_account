package me.oussamamessaoudi.openingaccount.application.service;

import java.math.BigDecimal;
import java.util.Optional;
import lombok.AllArgsConstructor;
import me.oussamamessaoudi.openingaccount.application.domain.entity.Account;
import me.oussamamessaoudi.openingaccount.application.domain.entity.Transaction;
import me.oussamamessaoudi.openingaccount.application.domain.repository.AccountRepository;
import me.oussamamessaoudi.openingaccount.application.domain.repository.TransactionRepository;
import me.oussamamessaoudi.openingaccount.application.exception.CodeError;
import me.oussamamessaoudi.openingaccount.application.exception.OpeningAccountException;
import me.oussamamessaoudi.openingaccount.application.helpers.FaultTolerantFunction;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
public class TransactionService {

  private TransactionRepository transactionRepository;
  private AccountRepository accountRepository;

  @Transactional
  public Account createTransaction(Transaction transactionToBeAdded) {
    return Optional.ofNullable(transactionToBeAdded)
        .filter(
            transaction ->
                Optional.of(transaction)
                        .map(Transaction::getAmount)
                        .orElse(BigDecimal.ZERO)
                        .signum()
                    > 0)
        .or(
            () -> {
              throw OpeningAccountException.builder()
                  .codeError(CodeError.TRANSACTION_AMOUNT_INVALID)
                  .build()
                  .addParam(
                      Optional.ofNullable(transactionToBeAdded)
                          .map(Transaction::getAmount)
                          .orElse(null));
            })
        .map(FaultTolerantFunction.of(transactionRepository::save))
        .map(FaultTolerantFunction.of(accountRepository::updateBalance))
        .orElseThrow(
            () ->
                OpeningAccountException.builder()
                    .codeError(CodeError.ERROR_CREATING_TRANSACTION)
                    .build()
                    .addParam(
                        Optional.of(transactionToBeAdded)
                            .map(Transaction::getAccount)
                            .map(Account::getId)
                            .orElse(null)));
  }
}
