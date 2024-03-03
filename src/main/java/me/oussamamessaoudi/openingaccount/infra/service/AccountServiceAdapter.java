package me.oussamamessaoudi.openingaccount.infra.service;

import me.oussamamessaoudi.openingaccount.application.domain.repository.AccountRepository;
import me.oussamamessaoudi.openingaccount.application.domain.repository.CustomerRepository;
import me.oussamamessaoudi.openingaccount.application.mapper.AccountMapper;
import me.oussamamessaoudi.openingaccount.application.service.AccountService;
import me.oussamamessaoudi.openingaccount.application.service.TransactionService;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceAdapter extends AccountService {
    public AccountServiceAdapter(CustomerRepository customerRepository, AccountRepository accountRepository, TransactionService transactionService, AccountMapper accountMapper) {
        super(customerRepository, accountRepository, transactionService, accountMapper);
    }
}
