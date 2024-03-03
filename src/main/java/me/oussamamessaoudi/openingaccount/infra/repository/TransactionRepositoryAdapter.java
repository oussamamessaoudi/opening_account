package me.oussamamessaoudi.openingaccount.infra.repository;

import me.oussamamessaoudi.openingaccount.application.domain.entity.Transaction;
import me.oussamamessaoudi.openingaccount.application.domain.repository.TransactionRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepositoryAdapter
    extends TransactionRepository, CrudRepository<Transaction, Long> {}
