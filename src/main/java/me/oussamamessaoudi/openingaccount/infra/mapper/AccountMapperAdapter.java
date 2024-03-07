package me.oussamamessaoudi.openingaccount.infra.mapper;

import me.oussamamessaoudi.openingaccount.application.domain.entity.Account;
import me.oussamamessaoudi.openingaccount.application.domain.model.NewAccountCreatedDTO;
import me.oussamamessaoudi.openingaccount.application.mapper.AccountMapper;
import me.oussamamessaoudi.openingaccount.application.mapper.TransactionMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = TransactionMapper.class)
public interface AccountMapperAdapter extends AccountMapper {
  @Mapping(target = "accountId", source = "id")
  NewAccountCreatedDTO mapAccountToNewAccountCreatedDTO(Account account);
}
