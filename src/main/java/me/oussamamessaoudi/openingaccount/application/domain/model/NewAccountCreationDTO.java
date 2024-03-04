package me.oussamamessaoudi.openingaccount.application.domain.model;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NewAccountCreationDTO {

  private long customerId;
  private BigDecimal initialCredit;
}
