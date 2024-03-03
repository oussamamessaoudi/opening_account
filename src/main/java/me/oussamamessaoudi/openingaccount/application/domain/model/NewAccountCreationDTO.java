package me.oussamamessaoudi.openingaccount.application.domain.model;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class NewAccountCreationDTO {

  private long customerId;
  private BigDecimal initialCredit;
}
