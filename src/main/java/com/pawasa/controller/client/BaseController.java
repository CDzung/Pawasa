package com.pawasa.controller.client;

import com.pawasa.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BaseController {

    @Autowired
    private ProductRepository productRepository;
    @GetMapping("/")
    public String index() {
        return "pages/client/home";
    }

    @GetMapping("/product")
    public String product(Model model, @RequestParam(name="id") Long id) {
        model.addAttribute("product", productRepository.findById(id).get());
        return "pages/client/bookdetail";
    }
}
