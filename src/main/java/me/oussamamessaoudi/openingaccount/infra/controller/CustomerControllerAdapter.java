package me.oussamamessaoudi.openingaccount.infra.controller;

import me.oussamamessaoudi.openingaccount.application.controller.CustomerController;
import me.oussamamessaoudi.openingaccount.application.domain.model.CustomerDTO;
import me.oussamamessaoudi.openingaccount.application.service.CustomerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
public class CustomerControllerAdapter extends CustomerController {
  public CustomerControllerAdapter(CustomerService customerService) {
    super(customerService);
  }

  @Override
  @GetMapping
  public CustomerDTO getDetailCustomer(@RequestParam("id") long id) {
    return super.getDetailCustomer(id);
  }
}
