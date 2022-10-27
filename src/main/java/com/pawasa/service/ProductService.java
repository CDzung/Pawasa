package com.pawasa.service;

import com.pawasa.model.Product;

import java.util.Set;

public interface ProductService {
    void addProduct(Product product);

    Set<Product> getProductsByCategory(int id,Set<Product> set);
}
