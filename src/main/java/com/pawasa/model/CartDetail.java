package com.pawasa.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@ToString
public class CartDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "cart_id")
    private Long cartId;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "quantity")
    private Long quantity;

    public CartDetail() {
    }

    public CartDetail(Long id, Long cartId, Long productId, Long quantity) {
        this.id = id;
        this.cartId = cartId;
        this.productId = productId;
        this.quantity = quantity;
    }
}
