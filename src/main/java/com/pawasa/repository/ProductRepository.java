package com.pawasa.repository;

import com.pawasa.model.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByProductName(String productName);

    @Query(value = "select p from Product p")
    public Set<Product> searchAll();

    @Query(value = "select p from Product p where p.productName = '1'")
    public Set<Product> searchByProductName(String name, Sort sort);
}
