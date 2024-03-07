package me.oussamamessaoudi.openingaccount.infra.service;

import me.oussamamessaoudi.openingaccount.application.domain.repository.CustomerRepository;
import me.oussamamessaoudi.openingaccount.application.mapper.CustomerMapper;
import me.oussamamessaoudi.openingaccount.application.service.CustomerService;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceAdapter extends CustomerService {
  public CustomerServiceAdapter(
      CustomerRepository customerRepository, CustomerMapper customerMapper) {
    super(customerRepository, customerMapper);
  }
}
