package me.oussamamessaoudi.openingaccount.application.service;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Optional;
import me.oussamamessaoudi.openingaccount.application.domain.entity.Account;
import me.oussamamessaoudi.openingaccount.application.domain.entity.Transaction;
import me.oussamamessaoudi.openingaccount.application.domain.repository.AccountRepository;
import me.oussamamessaoudi.openingaccount.application.domain.repository.TransactionRepository;
import me.oussamamessaoudi.openingaccount.application.exception.CodeError;
import me.oussamamessaoudi.openingaccount.application.exception.OpeningAccountException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

  @Mock TransactionRepository transactionRepository;
  @Mock AccountRepository accountRepository;

  @InjectMocks TransactionService transactionService;

  @ParameterizedTest
  @NullSource
  @ValueSource(doubles = {0, -10, -0.1, -999999, -9999.88788})
  void createTransaction_invalidAmount_throwException(Double value) {
    var amount = Optional.ofNullable(value).map(BigDecimal::new).orElse(null);
    var ex =
        assertThrows(
            OpeningAccountException.class,
            () ->
                transactionService.createTransaction(Transaction.builder().amount(amount).build()));
    Assertions.assertAll(
        () -> assertEquals(CodeError.TRANSACTION_AMOUNT_INVALID, ex.getCodeError()),
        () -> assertEquals(500, ex.getHttpStatus()),
        () -> assertEquals(1, ex.getParams().size()),
        () -> assertEquals(amount, ex.getParams().get(0)));
  }

  @ParameterizedTest
  @ValueSource(doubles = {1, 10, 0.1, 999999, 9999.88788})
  void createTransaction_validAmountAndTransactionServiceIsDown_throwException(Double value) {
    var amount = Optional.ofNullable(value).map(BigDecimal::new).orElse(null);
    Mockito.when(transactionRepository.save(Mockito.any(Transaction.class)))
        .thenThrow(new RuntimeException());
    var ex =
        assertThrows(
            OpeningAccountException.class,
            () ->
                transactionService.createTransaction(
                    Transaction.builder()
                        .amount(amount)
                        .account(Account.builder().id(1L).build())
                        .build()));
    Assertions.assertAll(
        () -> assertEquals(CodeError.ERROR_CREATING_TRANSACTION, ex.getCodeError()),
        () -> assertEquals(500, ex.getHttpStatus()),
        () -> assertEquals(1, ex.getParams().size()),
        () -> assertEquals(1L, ex.getParams().get(0)));
  }

  @ParameterizedTest
  @ValueSource(doubles = {1, 10, 0.1, 999999, 9999.88788})
  void createTransaction_validAmountAndUpdateAccountServiceIsDown_throwException(Double value) {
    var amount = Optional.ofNullable(value).map(BigDecimal::new).orElse(null);
    Mockito.when(transactionRepository.save(Mockito.any(Transaction.class)))
        .thenReturn(
            Transaction.builder()
                .id(1L)
                .amount(amount)
                .account(Account.builder().id(1L).build())
                .build());
    Mockito.when(accountRepository.updateBalance(Mockito.any(Transaction.class)))
        .thenThrow(new RuntimeException());
    var ex =
        assertThrows(
            OpeningAccountException.class,
            () ->
                transactionService.createTransaction(
                    Transaction.builder()
                        .amount(amount)
                        .account(Account.builder().id(1L).build())
                        .build()));
    Assertions.assertAll(
        () -> assertEquals(CodeError.ERROR_CREATING_TRANSACTION, ex.getCodeError()),
        () -> assertEquals(500, ex.getHttpStatus()),
        () -> assertEquals(1, ex.getParams().size()),
        () -> assertEquals(1L, ex.getParams().get(0)));
  }

  @ParameterizedTest
  @ValueSource(doubles = {1, 10, 0.1, 999999, 9999.88788})
  void createTransaction_validAmountAndServicesUp_validCreation(Double value) {
    var amount = BigDecimal.valueOf(value);
    Mockito.when(transactionRepository.save(Mockito.any(Transaction.class)))
        .thenReturn(
            Transaction.builder()
                .id(1L)
                .amount(amount)
                .account(Account.builder().id(1L).build())
                .build());
    Mockito.when(accountRepository.updateBalance(Mockito.any(Transaction.class)))
        .thenReturn(Account.builder().id(1L).balance(amount).build());
    var account =
        transactionService.createTransaction(
            Transaction.builder().amount(amount).account(Account.builder().id(1L).build()).build());
    Assertions.assertAll(
        () -> assertEquals(amount, account.getBalance()), () -> assertEquals(1L, account.getId()));
  }
}
