package me.oussamamessaoudi.openingaccount.application.domain.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Set;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {

  private Long id;

  @Column private String label;

  private BigDecimal balance;

  private Set<TransactionDTO> transactions;
}
