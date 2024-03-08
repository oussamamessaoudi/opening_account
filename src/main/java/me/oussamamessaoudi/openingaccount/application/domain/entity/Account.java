package me.oussamamessaoudi.openingaccount.application.domain.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Set;
import lombok.*;

@Table
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {
  @Id
  @Column
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column private Long customerId;

  @Column private String label;

  @Column(nullable = false)
  @Builder.Default
  private BigDecimal balance = BigDecimal.ZERO;

  @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
  private Set<Transaction> transactions;
}
