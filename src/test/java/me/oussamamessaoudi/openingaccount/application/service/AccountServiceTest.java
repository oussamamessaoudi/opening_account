package me.oussamamessaoudi.openingaccount.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

import java.math.BigDecimal;
import java.util.Optional;
import me.oussamamessaoudi.openingaccount.application.domain.entity.Account;
import me.oussamamessaoudi.openingaccount.application.domain.entity.Customer;
import me.oussamamessaoudi.openingaccount.application.domain.model.NewAccountCreatedDTO;
import me.oussamamessaoudi.openingaccount.application.domain.model.NewAccountCreationDTO;
import me.oussamamessaoudi.openingaccount.application.domain.repository.AccountRepository;
import me.oussamamessaoudi.openingaccount.application.domain.repository.CustomerRepository;
import me.oussamamessaoudi.openingaccount.application.exception.CodeError;
import me.oussamamessaoudi.openingaccount.application.exception.OpeningAccountException;
import me.oussamamessaoudi.openingaccount.application.mapper.AccountMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

  @Mock CustomerRepository customerRepository;
  @Mock private AccountRepository accountRepository;
  @Mock private TransactionService transactionService;
  @Mock private AccountMapper accountMapper;

  @InjectMocks AccountService accountService;

  @Test
  void createAccount_CustomerServiceUnavailable_throwException() {
    Mockito.when(customerRepository.getByCustomerId(anyLong())).thenThrow(new RuntimeException());

    var ex =
        assertThrows(
            OpeningAccountException.class,
            () ->
                accountService.createAccount(
                    NewAccountCreationDTO.builder()
                        .customerId(12)
                        .initialCredit(BigDecimal.TEN)
                        .build()));
    Assertions.assertAll(
        () -> assertEquals(CodeError.CUSTOMER_SERVICE_UNAVAILABLE, ex.getCodeError()),
        () -> assertEquals(500, ex.getHttpStatus()),
        () -> assertNull(ex.getParams()));
  }

  @Test
  void createAccount_CustomerNotFound_throwException() {
    Mockito.when(customerRepository.getByCustomerId(anyLong())).thenReturn(Optional.empty());

    var ex =
        assertThrows(
            OpeningAccountException.class,
            () ->
                accountService.createAccount(
                    NewAccountCreationDTO.builder()
                        .customerId(12)
                        .initialCredit(BigDecimal.TEN)
                        .build()));
    Assertions.assertAll(
        () -> assertEquals(CodeError.CUSTOMER_NOT_FOUND, ex.getCodeError()),
        () -> assertEquals(404, ex.getHttpStatus()),
        () -> assertEquals(1, ex.getParams().size()),
        () -> assertEquals(12L, ex.getParams().get(0)));
  }

  @Test
  void createAccount_AccountServiceUnavailable_throwException() {
    Mockito.when(customerRepository.getByCustomerId(anyLong()))
        .thenReturn(
            Optional.of(Customer.builder().customerId(12L).name("test").surname("test").build()));

    Mockito.when(accountRepository.save(any())).thenThrow(new RuntimeException());

    var ex =
        assertThrows(
            OpeningAccountException.class,
            () ->
                accountService.createAccount(
                    NewAccountCreationDTO.builder()
                        .customerId(12)
                        .initialCredit(BigDecimal.TEN)
                        .build()));
    Assertions.assertAll(
        () -> assertEquals(CodeError.ERROR_CREATING_ACCOUNT, ex.getCodeError()),
        () -> assertEquals(500, ex.getHttpStatus()),
        () -> assertEquals(1, ex.getParams().size()),
        () -> assertEquals(12L, ex.getParams().get(0)));
  }

  @Test
  void createAccount_CustomerExistsServicesAvailable_valid() {
    Customer customer = Customer.builder().customerId(12L).name("test").surname("test").build();
    Mockito.when(customerRepository.getByCustomerId(anyLong())).thenReturn(Optional.of(customer));

    Mockito.when(accountRepository.save(any()))
        .thenReturn(Account.builder().id(33L).customer(customer).balance(BigDecimal.ZERO).build());

    var account =
        accountService.createAccount(
            NewAccountCreationDTO.builder().customerId(12).initialCredit(BigDecimal.TEN).build());
    Assertions.assertAll(
        () -> assertTrue(account.isPresent()),
        () -> assertEquals(33, account.get().getId()),
        () -> assertEquals(12L, account.get().getCustomer().getCustomerId()),
        () -> assertEquals(BigDecimal.ZERO, account.get().getBalance()));
  }

  @Test
  void createAccountWithDeposit_CustomerExistsWithValidDeposit_valid() {
    Customer customer = Customer.builder().customerId(12L).name("test").surname("test").build();
    Mockito.when(customerRepository.getByCustomerId(anyLong())).thenReturn(Optional.of(customer));

    Mockito.when(accountRepository.save(any()))
        .thenReturn(Account.builder().id(33L).customer(customer).balance(BigDecimal.ZERO).build());

    Mockito.when(transactionService.createTransaction(any()))
        .thenReturn(Account.builder().balance(BigDecimal.TEN).build());

    Mockito.when(accountMapper.mapAccountToNewAccountCreatedDTO(any()))
        .thenReturn(
            NewAccountCreatedDTO.builder()
                .customerId(1L)
                .balance(BigDecimal.TEN)
                .accountId(2L)
                .build());

    var createdAccount =
        accountService.createAccountWithDeposit(
            NewAccountCreationDTO.builder().customerId(12).initialCredit(BigDecimal.TEN).build());
    Assertions.assertAll(
        () -> assertEquals(1L, createdAccount.getCustomerId()),
        () -> assertEquals(2L, createdAccount.getAccountId()),
        () -> assertEquals(BigDecimal.TEN, createdAccount.getBalance()));
  }

  @Test
  void createAccountWithDeposit_CustomerExistsWithInvalidDeposit_valid() {
    Customer customer = Customer.builder().customerId(12L).name("test").surname("test").build();
    Mockito.when(customerRepository.getByCustomerId(anyLong())).thenReturn(Optional.of(customer));

    Mockito.when(accountRepository.save(any()))
        .thenReturn(Account.builder().id(33L).customer(customer).balance(BigDecimal.ZERO).build());

    Mockito.when(transactionService.createTransaction(any())).thenThrow(new RuntimeException());

    Mockito.when(accountMapper.mapAccountToNewAccountCreatedDTO(any()))
        .thenReturn(
            NewAccountCreatedDTO.builder()
                .customerId(1L)
                .balance(BigDecimal.ZERO)
                .accountId(2L)
                .build());

    var createdAccount =
        accountService.createAccountWithDeposit(
            NewAccountCreationDTO.builder().customerId(12).initialCredit(BigDecimal.TEN).build());
    Assertions.assertAll(
        () -> assertEquals(1L, createdAccount.getCustomerId()),
        () -> assertEquals(2L, createdAccount.getAccountId()),
        () -> assertEquals(BigDecimal.ZERO, createdAccount.getBalance()));
  }

  @Test
  void createAccountWithDeposit_accountNotCreatedDueToInternalError_valid() {
    Customer customer = Customer.builder().customerId(12L).name("test").surname("test").build();
    Mockito.when(customerRepository.getByCustomerId(anyLong())).thenReturn(Optional.of(customer));

    Mockito.when(accountRepository.save(any()))
        .thenReturn(Account.builder().id(33L).customer(customer).balance(BigDecimal.ZERO).build());

    Mockito.when(transactionService.createTransaction(any())).thenThrow(new RuntimeException());

    Mockito.when(accountMapper.mapAccountToNewAccountCreatedDTO(any())).thenReturn(null);

    var ex =
        assertThrows(
            OpeningAccountException.class,
            () ->
                accountService.createAccountWithDeposit(
                    NewAccountCreationDTO.builder()
                        .customerId(12)
                        .initialCredit(BigDecimal.TEN)
                        .build()));
    Assertions.assertAll(
        () -> assertEquals(CodeError.INTERNAL_ERROR, ex.getCodeError()),
        () -> assertEquals(500, ex.getHttpStatus()),
        () -> assertNull(ex.getParams()));
  }
}
