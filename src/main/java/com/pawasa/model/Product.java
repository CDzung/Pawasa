package com.pawasa.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //@NotEmpty
    @Column(name = "product_name")
    private String productName;

    //@NotEmpty
    @Column(name = "description")
    private String description;

    //@NotNull
    @Column(name = "price")
    private double price;

    //@NotEmpty
    @Column(name = "image")
    private String image;

    //@NotNull
    @Column(name = "quantity")
    private Long quantity;

    //@NotNull
    @Column(name = "discount")
    private int discount;

    //@NotEmpty
    @Column(name = "isbn")
    private String isbn;

    //@NotEmpty
    @Column(name = "author")
    private String author;

    //@NotEmpty
    @Column(name = "publisher")
    private String publisher;

    //@NotEmpty
    @Column(name = "publish_year")
    private String publishYear;

    //@NotEmpty
    private String language;
    //@NotEmpty
    private String supplier;

    //@NotEmpty
    @Column(name = "book_layout")
    private String bookLayout;

    @Column(name = "available")
    private boolean available;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    private Category category;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "cartdetail",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "cart_id"))
    private Set<Cart> carts;

    @OneToMany(mappedBy = "product")
    private Set<CartDetail> cartDetails;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "orderdetail",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "order_id"))
    private Set<Order> orders;

    @OneToMany(mappedBy = "product")
    private Set<OrderDetail> orderDetails;

    public String getFormattedPriceString(double price) {
        String s = (long) price + "";
        s=s.trim();
        String priceString = "";
        for(int i=s.length()-1;i>=0;i--) {
            priceString = s.charAt(i) + priceString;
            if((s.length()-i) % 3 == 0 && i!=0)
                priceString = "." + priceString;
        }
        return priceString;
    }


}
