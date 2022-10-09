package com.pawasa.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@ToString
public class Payment {
    @Id
    @Column(name = "payment_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "payment_method")
    private String paymentMethod;

    @OneToMany(mappedBy = "payment")
    private Set<Order> orders;
}
