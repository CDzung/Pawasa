package com.pawasa.repository;

import com.pawasa.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByProductName(String productName);

    @Query(value = "Select * from product order by product_id desc limit 10", nativeQuery = true)
    Set<Product> findTopNewProduct();

    @Query(value = "Select * from product order by discount desc limit 10 ", nativeQuery = true)
    Set<Product> findTopDiscountProduct();

    Set<Product> findByOrderByDiscountDesc();




}
