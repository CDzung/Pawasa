package com.pawasa.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@ToString
public class Order {
    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderId;

    @Column(name = "order_date")
    private Date orderDate;

    @Column(name = "payment_id")
    private Long paymentId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "total_price")
    private Double totalPrice;

    public Order() {
    }

    public Order(Long orderId, Date orderDate, Long paymentId, Long userId, Double totalPrice) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.paymentId = paymentId;
        this.userId = userId;
        this.totalPrice = totalPrice;
    }
}
