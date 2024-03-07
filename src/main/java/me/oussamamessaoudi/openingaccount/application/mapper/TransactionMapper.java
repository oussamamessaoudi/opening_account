package me.oussamamessaoudi.openingaccount.application.mapper;

import me.oussamamessaoudi.openingaccount.application.domain.entity.Transaction;
import me.oussamamessaoudi.openingaccount.application.domain.model.TransactionDTO;

public interface TransactionMapper {
  TransactionDTO mapTransactionToTransactionDTO(Transaction transaction);
}
