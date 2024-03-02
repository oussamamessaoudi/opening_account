package me.oussamamessaoudi.openingaccount.application.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Table
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @Column
    @GeneratedValue
    private Long customerId;

    @Column
    private String name;

    @Column
    private String surname;

}
