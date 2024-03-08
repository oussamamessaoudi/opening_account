package me.oussamamessaoudi.openingaccount.application.mapper;

import me.oussamamessaoudi.openingaccount.application.domain.entity.Account;
import me.oussamamessaoudi.openingaccount.application.domain.model.AccountDTO;
import me.oussamamessaoudi.openingaccount.application.domain.model.NewAccountCreatedDTO;

public interface AccountMapper {
  NewAccountCreatedDTO mapAccountToNewAccountCreatedDTO(Account account);

  AccountDTO mapAccountToAccountDTO(Account account);
}
