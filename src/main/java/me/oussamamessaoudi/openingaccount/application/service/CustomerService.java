package me.oussamamessaoudi.openingaccount.application.service;

import java.util.Optional;
import lombok.AllArgsConstructor;
import me.oussamamessaoudi.openingaccount.application.domain.model.CustomerDTO;
import me.oussamamessaoudi.openingaccount.application.domain.repository.CustomerRepository;
import me.oussamamessaoudi.openingaccount.application.exception.CodeError;
import me.oussamamessaoudi.openingaccount.application.exception.OpeningAccountException;
import me.oussamamessaoudi.openingaccount.application.helpers.FaultTolerantFunction;
import me.oussamamessaoudi.openingaccount.application.helpers.FaultTolerantPredicate;
import me.oussamamessaoudi.openingaccount.application.mapper.CustomerMapper;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public class CustomerService {

  private CustomerRepository customerRepository;
  private CustomerMapper customerMapper;

  public CustomerDTO getDetailCustomer(long customerId) {

    return Optional.of(customerId)
        .flatMap(
            FaultTolerantFunction.of(
                customerRepository::getByCustomerId,
                id -> {
                  throw OpeningAccountException.builder()
                      .codeError(CodeError.CUSTOMER_SERVICE_UNAVAILABLE)
                      .build();
                }))
        .map(customerMapper::mapCustomerToCustomerDTO)
        .orElseThrow(
            () ->
                OpeningAccountException.builder()
                    .codeError(CodeError.CUSTOMER_NOT_FOUND)
                    .httpStatus(HttpStatus.NOT_FOUND.value())
                    .build()
                    .addParam(customerId));
  }

  public boolean checkCustomerExistence(Long idCustomer) {
    return FaultTolerantPredicate.of(
            customerRepository::existsByCustomerId,
            id -> {
              throw OpeningAccountException.builder()
                  .codeError(CodeError.CUSTOMER_SERVICE_UNAVAILABLE)
                  .build();
            })
        .test(idCustomer);
  }
}
