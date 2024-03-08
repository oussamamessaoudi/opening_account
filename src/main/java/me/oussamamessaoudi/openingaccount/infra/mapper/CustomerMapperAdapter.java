package me.oussamamessaoudi.openingaccount.infra.mapper;

import me.oussamamessaoudi.openingaccount.application.mapper.AccountMapper;
import me.oussamamessaoudi.openingaccount.application.mapper.CustomerMapper;
import org.mapstruct.Mapper;

@Mapper(uses = AccountMapper.class)
public interface CustomerMapperAdapter extends CustomerMapper {}
