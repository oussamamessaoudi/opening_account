package me.oussamamessaoudi.openingaccount.application.domain.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
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

  @ManyToOne
  @JoinColumn(name = "customer_id")
  private Customer customer;

  @Column private String label;

  @Column(nullable = false)
  @Builder.Default
  private BigDecimal balance = BigDecimal.ZERO;
}
