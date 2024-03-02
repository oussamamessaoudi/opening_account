package me.oussamamessaoudi.openingaccount.application.domain.repository;

import me.oussamamessaoudi.openingaccount.application.domain.entity.Customer;

import java.util.Optional;


public interface CustomerRepository {
    Optional<Customer> getByCustomerId(Long id);
}
