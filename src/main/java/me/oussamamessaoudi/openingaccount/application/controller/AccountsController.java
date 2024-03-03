package me.oussamamessaoudi.openingaccount.application.controller;

import lombok.AllArgsConstructor;
import me.oussamamessaoudi.openingaccount.application.domain.model.NewAccountCreatedDTO;
import me.oussamamessaoudi.openingaccount.application.domain.model.NewAccountCreationDTO;
import me.oussamamessaoudi.openingaccount.application.service.AccountService;

@AllArgsConstructor
public class AccountsController {

    private AccountService accountService;

    public NewAccountCreatedDTO createAccount(NewAccountCreationDTO newAccountCreation) {
        return accountService.createNewAccount(newAccountCreation);
    }

}
