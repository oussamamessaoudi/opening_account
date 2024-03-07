package me.oussamamessaoudi.openingaccount.application.domain.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.*;

@Data
@Table
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

  @Id
  @Column
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "account_id")
  private Account account;

  @Column private BigDecimal amount;

  @Column private String label;
}
