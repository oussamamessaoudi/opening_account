package me.oussamamessaoudi.openingaccount.application.domain.repository;

import me.oussamamessaoudi.openingaccount.application.domain.entity.Transaction;

public interface TransactionRepository {
    Transaction save(Transaction transaction);

}
