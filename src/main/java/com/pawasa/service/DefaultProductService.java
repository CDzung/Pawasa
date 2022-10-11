package com.pawasa.service;

import com.pawasa.model.Product;
import com.pawasa.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("productService")
public class DefaultProductService implements ProductService{

    @Autowired
    private ProductRepository productRepository;


    @Override
    public void addProduct(Product product) {
        productRepository.save(product);
    }
}
