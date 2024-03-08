package me.oussamamessaoudi.openingaccount.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;

import java.util.Optional;
import me.oussamamessaoudi.openingaccount.application.domain.entity.Customer;
import me.oussamamessaoudi.openingaccount.application.domain.model.CustomerDTO;
import me.oussamamessaoudi.openingaccount.application.domain.repository.CustomerRepository;
import me.oussamamessaoudi.openingaccount.application.exception.CodeError;
import me.oussamamessaoudi.openingaccount.application.exception.OpeningAccountException;
import me.oussamamessaoudi.openingaccount.application.mapper.CustomerMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

  @Mock CustomerRepository customerRepository;
  @Mock CustomerMapper customerMapper;

  @InjectMocks CustomerService customerService;

  @Test
  void checkCustomerExistence_customerPresent_isPresent() {
    Mockito.when(customerRepository.existsByCustomerId(anyLong())).thenReturn(true);
    assertTrue(customerService.checkCustomerExistence(10L));
  }

  @Test
  void checkCustomerExistence_customerNotPresent_isAbsent() {
    Mockito.when(customerRepository.existsByCustomerId(anyLong())).thenReturn(false);
    assertFalse(customerService.checkCustomerExistence(10L));
  }

  @Test
  void checkCustomerExistence_customerServiceUnavailable_isAbsent() {
    Mockito.when(customerRepository.existsByCustomerId(anyLong()))
        .thenThrow(new RuntimeException());
    var ex =
        Assertions.assertThrows(
            OpeningAccountException.class, () -> customerService.checkCustomerExistence(10L));

    assertAll(
        () -> assertEquals(CodeError.CUSTOMER_SERVICE_UNAVAILABLE, ex.getCodeError()),
        () -> assertEquals(500, ex.getHttpStatus()),
        () -> assertNull(ex.getParams()));
  }

  @Test
  void getDetailCustomer_customerIsPresent_valid() {
    Mockito.when(customerRepository.getByCustomerId(anyLong()))
        .thenReturn(
            Optional.of(
                Customer.builder().customerId(1L).name("Messaoudi").surname("Oussama").build()));
    Mockito.when(
            customerMapper.mapCustomerToCustomerDTO(
                Customer.builder().customerId(1L).name("Messaoudi").surname("Oussama").build()))
        .thenReturn(
            CustomerDTO.builder().customerId(1L).name("Messaoudi").surname("Oussama").build());

    var customer = customerService.getDetailCustomer(1L);
    assertAll(
        () -> assertEquals(1L, customer.getCustomerId()),
        () -> assertEquals("Messaoudi", customer.getName()),
        () -> assertEquals("Oussama", customer.getSurname()));
  }

  @Test
  void getDetailCustomer_customerAbsent_throwException() {
    Mockito.when(customerRepository.getByCustomerId(anyLong())).thenReturn(Optional.empty());

    var ex =
        assertThrows(OpeningAccountException.class, () -> customerService.getDetailCustomer(1L));
    assertAll(
        () -> assertEquals(CodeError.CUSTOMER_NOT_FOUND, ex.getCodeError()),
        () -> assertEquals(404, ex.getHttpStatus()),
        () -> assertEquals(1, ex.getParams().size()),
        () -> assertEquals(1L, ex.getParams().get(0)));
  }

  @Test
  void getDetailCustomer_customerServiceUnavailable_throwException() {
    Mockito.when(customerRepository.getByCustomerId(anyLong())).thenThrow(new RuntimeException());

    var ex =
        assertThrows(OpeningAccountException.class, () -> customerService.getDetailCustomer(1L));
    assertAll(
        () -> assertEquals(CodeError.CUSTOMER_SERVICE_UNAVAILABLE, ex.getCodeError()),
        () -> assertEquals(500, ex.getHttpStatus()),
        () -> assertNull(ex.getParams()));
  }
}
