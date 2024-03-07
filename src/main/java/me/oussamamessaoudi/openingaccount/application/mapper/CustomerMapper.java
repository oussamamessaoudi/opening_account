package me.oussamamessaoudi.openingaccount.application.mapper;

import me.oussamamessaoudi.openingaccount.application.domain.entity.Customer;
import me.oussamamessaoudi.openingaccount.application.domain.model.CustomerDTO;

public interface CustomerMapper {

  CustomerDTO mapCustomerToCustomerDTO(Customer customer);
}
