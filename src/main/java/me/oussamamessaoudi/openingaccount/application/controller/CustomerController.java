package me.oussamamessaoudi.openingaccount.application.controller;

import lombok.AllArgsConstructor;
import me.oussamamessaoudi.openingaccount.application.domain.model.CustomerDTO;
import me.oussamamessaoudi.openingaccount.application.service.CustomerService;

@AllArgsConstructor
public class CustomerController {
  CustomerService customerService;

  public CustomerDTO getDetailCustomer(long id) {
    return customerService.getDetailCustomer(id);
  }
}
