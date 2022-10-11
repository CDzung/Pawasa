package com.pawasa.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BaseController {

        @GetMapping("/")
        public String index() {
            return "pages/client/home";
        }
}
