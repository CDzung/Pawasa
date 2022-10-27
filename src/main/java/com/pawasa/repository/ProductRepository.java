package com.pawasa.repository;

import com.pawasa.model.Category;
import com.pawasa.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>,
        JpaSpecificationExecutor<Product> {
    Product findByProductName(String productName);
    @Query(value = "Select c from Category c WHERE c.id = ?1")
    Category findByCategoryID(Long id);
    @Query(value = "select p from Product p")
    public List<Product> searchAll();
    @Query(value = "select p from Product p where p.productName Like ?1%")
    public List<Product> searchByProductName(String name, Sort sort);
    @Query(value = "select p from Product p where p.productName LIKE ?1")
    Page<Product> searchByNameContain(String name, Pageable pageable);
    @Query(value = "select p from Product p where p.category.id = ?1")
    Page<Product> searchByCategory(Long categoryId, Pageable pageable);

    Page<Product> findAll(Specification<Product> var, Pageable pageable);

}
