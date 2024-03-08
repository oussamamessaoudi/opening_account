package me.oussamamessaoudi.openingaccount.application.domain.model;

import jakarta.persistence.*;
import java.util.Set;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

  private Long customerId;

  @Column private String name;

  @Column private String surname;

  private Set<AccountDTO> accounts;
}
