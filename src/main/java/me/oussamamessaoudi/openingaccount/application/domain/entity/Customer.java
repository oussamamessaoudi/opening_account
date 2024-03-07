package me.oussamamessaoudi.openingaccount.application.domain.entity;

import jakarta.persistence.*;
import java.util.Set;
import lombok.*;

@Table
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
  @Id
  @Column
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long customerId;

  @Column private String name;

  @Column private String surname;

  @EqualsAndHashCode.Exclude
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "customerId")
  private Set<Account> accounts;
}
