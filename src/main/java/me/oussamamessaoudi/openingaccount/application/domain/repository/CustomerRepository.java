package me.oussamamessaoudi.openingaccount.application.domain.repository;

import java.util.Optional;
import me.oussamamessaoudi.openingaccount.application.domain.entity.Customer;

public interface CustomerRepository {
  Optional<Customer> getByCustomerId(Long id);

  boolean existsByCustomerId(Long id);
}
