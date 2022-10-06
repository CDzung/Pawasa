package com.pawasa.model;


import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@ToString
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "quantity")
    private int quantity;

    private int rate;

    private String feedback;

    public OrderDetail() {
    }

    public OrderDetail(Long id, Long orderId, Long productId, int quantity, int rate, String feedback) {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.rate = rate;
        this.feedback = feedback;
    }
}
