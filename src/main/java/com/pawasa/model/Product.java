package com.pawasa.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@ToString
public class Product {
    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Double price;

    @Column(name = "category_id")
    private Long categoryID;

    @Column(name = "image")
    private String image;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "discount")
    private int discount;

    @Column(name = "isbn")
    private String isbn;

    @Column(name = "author")
    private String author;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "publish_year")
    private String publishYear;

    private String language;

    private String supplier;

    public Product() {
    }

    public Product(Long id, String productName, String description, Double price, Long categoryID, String image, Long quantity, int discount, String isbn, String author, String publisher, String publishYear, String language, String supplier) {
        this.id = id;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.categoryID = categoryID;
        this.image = image;
        this.quantity = quantity;
        this.discount = discount;
        this.isbn = isbn;
        this.author = author;
        this.publisher = publisher;
        this.publishYear = publishYear;
        this.language = language;
        this.supplier = supplier;
    }
}
