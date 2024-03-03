package me.oussamamessaoudi.openingaccount.application.service;

import java.math.BigDecimal;
import java.util.Optional;
import lombok.AllArgsConstructor;
import me.oussamamessaoudi.openingaccount.application.domain.entity.Account;
import me.oussamamessaoudi.openingaccount.application.domain.entity.Transaction;
import me.oussamamessaoudi.openingaccount.application.domain.repository.AccountRepository;
import me.oussamamessaoudi.openingaccount.application.domain.repository.TransactionRepository;
import me.oussamamessaoudi.openingaccount.application.exception.CodeError;
import me.oussamamessaoudi.openingaccount.application.exception.ExceptionOpeningAccount;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
public class TransactionService {

  private TransactionRepository transactionRepository;
  private AccountRepository accountRepository;

  @Transactional
  public Account createTransaction(Transaction transactionToBeAdded) {
    return Optional.of(transactionToBeAdded)
        .filter(
            transaction ->
                Optional.of(transaction)
                        .map(Transaction::getAmount)
                        .orElse(BigDecimal.ZERO)
                        .signum()
                    <= 0)
        .or(
            () -> {
              throw ExceptionOpeningAccount.builder()
                  .codeError(CodeError.ERROR_CREATING_TRANSACTION)
                  .build()
                  .addParam(transactionToBeAdded.getAmount());
            })
        .map(transaction -> transactionRepository.save(transaction))
        .map(transaction -> accountRepository.updateBalance(transaction))
        .orElseThrow(
            () ->
                ExceptionOpeningAccount.builder()
                    .codeError(CodeError.ERROR_CREATING_TRANSACTION)
                    .build()
                    .addParam(transactionToBeAdded.getAccount().getId()));
  }
}
