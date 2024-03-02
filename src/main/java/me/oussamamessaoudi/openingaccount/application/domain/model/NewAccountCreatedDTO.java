package me.oussamamessaoudi.openingaccount.application.domain.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class NewAccountCreatedDTO {

    private Long customerId;
    private Long accountId;
    private BigDecimal balance;
}
