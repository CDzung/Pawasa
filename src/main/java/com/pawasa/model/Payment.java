package com.pawasa.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

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

    public Payment() {
    }

    public Payment(Long id, String paymentMethod) {
        this.id = id;
        this.paymentMethod = paymentMethod;
    }
}
