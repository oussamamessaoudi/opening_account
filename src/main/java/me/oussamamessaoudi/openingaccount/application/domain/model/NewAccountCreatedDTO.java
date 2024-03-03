package me.oussamamessaoudi.openingaccount.application.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewAccountCreatedDTO {
    private Long customerId;
    private Long accountId;
    private BigDecimal balance;
}
