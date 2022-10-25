package com.pawasa.service;

import com.pawasa.model.Category;
import com.pawasa.model.Product;
import com.pawasa.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service("productService")
public class DefaultProductService implements ProductService {

    @Autowired
    private ProductRepository productRepository;


    @Override
    public void addProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public Set<Product> getProductsByCategory(int id, Set<Product> set) {
        Set<Product> set_result = new HashSet<>();
        for (Product i : set) {
            Category p = i.getCategory();
            if (id == 1 || id == 2) {
                if (i.getCategory().getId() == id) {
                    set_result.add(i);
                } else {
                    while (p.getId() != id || p.getId() != p.getParentCategory().getId()){
                        p = p.getParentCategory();
                    }
                    if(p.getId() == id){
                        set_result.add(i);
                    }
                }
            } else {
                if (i.getCategory().getId() == id) {
                    set_result.add(i);
                } else {
                    while (p.getParentCategory().getId() != 1 && p.getParentCategory().getId() != id && p.getParentCategory().getId() != 2) {
                        p = p.getParentCategory();
                    }
                    if (p.getParentCategory().getId() != 1 && p.getParentCategory().getId() != 2) {
                        set_result.add(i);
                    }
                }
            }

        }
        return set_result;
    }
}
