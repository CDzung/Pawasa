package com.pawasa.controller.manager;

import com.pawasa.model.Category;
import com.pawasa.model.Product;
import com.pawasa.repository.CartRepository;
import com.pawasa.repository.CategoryRepository;
import com.pawasa.service.DefaultProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class ManagerController {

    @Autowired
    private DefaultProductService defaultProductService;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/manager/add-product")
    public String index(Model model) {
        model.addAttribute("product", new Product());
        return "pages/manager/addproduct";
    }

    @PostMapping("/manager/add-product")
    public String addProduct(@Valid Product product, @RequestParam(name="cate") String categoryName, Model model) {
        Category category = categoryRepository.findByCategoryName(categoryName);
        if(category!=null) {
            product.setCategory(category);
            defaultProductService.addProduct(product);
        }
        model.addAttribute("product", new Product());
        return "pages/manager/addproduct";
    }
}
