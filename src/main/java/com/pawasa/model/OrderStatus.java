package com.pawasa.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@ToString
public class OrderStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "status_name")
    private String orderStatus;

    @Column(name = "order_id")
    private int orderId;


    @Column(name = "status_date")
    private Date statusDate;

    public OrderStatus() {
    }

    public OrderStatus(Long id, String orderStatus, int orderId, Date statusDate) {
        this.id = id;
        this.orderStatus = orderStatus;
        this.orderId = orderId;
        this.statusDate = statusDate;
    }
}
