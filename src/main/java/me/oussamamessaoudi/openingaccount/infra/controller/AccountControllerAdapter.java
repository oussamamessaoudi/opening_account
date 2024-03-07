package me.oussamamessaoudi.openingaccount.infra.controller;

import me.oussamamessaoudi.openingaccount.application.controller.AccountsController;
import me.oussamamessaoudi.openingaccount.application.domain.model.NewAccountCreatedDTO;
import me.oussamamessaoudi.openingaccount.application.domain.model.NewAccountCreationDTO;
import me.oussamamessaoudi.openingaccount.application.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
public class AccountControllerAdapter extends AccountsController {
  public AccountControllerAdapter(AccountService accountService) {
    super(accountService);
  }

  @Override
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public NewAccountCreatedDTO createAccount(@RequestBody NewAccountCreationDTO newAccountCreation) {
    return super.createAccount(newAccountCreation);
  }
}
