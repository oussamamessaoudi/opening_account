package me.oussamamessaoudi.openingaccount.application.domain.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class NewAccountCreationDTO {

    private long customerId;
    private BigDecimal initialCredit;

}

