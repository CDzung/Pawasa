package com.pawasa.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderId;

    @Column(name = "order_date")
    private Date orderDate;

    @Column(name = "total_price")
    private Double totalPrice;

    @ManyToMany(mappedBy = "orders")
    private Set<Product> products;

    @OneToMany(mappedBy = "order")
    private Set<OrderDetail> orderDetails;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_id", referencedColumnName = "payment_id")
    private Payment payment;

    @OneToMany(mappedBy = "order")
    private Set<OrderStatus> orderStatuses;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;
}
