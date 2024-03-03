package me.oussamamessaoudi.openingaccount.infra.mapper;

import me.oussamamessaoudi.openingaccount.application.domain.entity.Account;
import me.oussamamessaoudi.openingaccount.application.domain.model.NewAccountCreatedDTO;
import me.oussamamessaoudi.openingaccount.application.mapper.AccountMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface AccountMapperAdapter extends AccountMapper {
    @Mapping(target = "accountId", source = "id")
    @Mapping(target = "customerId", source = "customer.customerId")
    @Mapping(source = "balance", target = "balance")
    NewAccountCreatedDTO mapAccountToNewAccountCreatedDTO(Account account);
}
