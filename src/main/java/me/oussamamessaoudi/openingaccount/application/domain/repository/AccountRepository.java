package me.oussamamessaoudi.openingaccount.application.domain.repository;

import me.oussamamessaoudi.openingaccount.application.domain.entity.Account;
import me.oussamamessaoudi.openingaccount.application.domain.entity.Transaction;

public interface AccountRepository {
    Account save(Account account);
    Account updateBalance(Transaction transaction);
}
