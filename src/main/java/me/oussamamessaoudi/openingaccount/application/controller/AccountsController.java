package me.oussamamessaoudi.openingaccount.application.controller;

import me.oussamamessaoudi.openingaccount.application.domain.model.NewAccountCreatedDTO;
import me.oussamamessaoudi.openingaccount.application.domain.model.NewAccountCreationDTO;
import me.oussamamessaoudi.openingaccount.application.service.AccountService;

public class AccountsController {

    private AccountService accountService;

    public NewAccountCreatedDTO createAccount(NewAccountCreationDTO newAccountCreation) {
        return accountService.createNewAccount(newAccountCreation);

    }
}
