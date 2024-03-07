package me.oussamamessaoudi.openingaccount.application.domain.model;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewAccountCreationDTO {

  private long customerId;
  private BigDecimal initialCredit;
}
