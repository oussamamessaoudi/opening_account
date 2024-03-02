package me.oussamamessaoudi.openingaccount.infra.repository;

import me.oussamamessaoudi.openingaccount.application.domain.entity.Customer;
import me.oussamamessaoudi.openingaccount.application.domain.repository.CustomerRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CustomerRepositoryAdapter extends CustomerRepository, CrudRepository<Customer, Long> {
}
