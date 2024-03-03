package me.oussamamessaoudi.openingaccount.infra.service;

import me.oussamamessaoudi.openingaccount.application.domain.repository.AccountRepository;
import me.oussamamessaoudi.openingaccount.application.domain.repository.TransactionRepository;
import me.oussamamessaoudi.openingaccount.application.service.TransactionService;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceAdapter extends TransactionService {
    public TransactionServiceAdapter(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        super(transactionRepository, accountRepository);
    }
}
