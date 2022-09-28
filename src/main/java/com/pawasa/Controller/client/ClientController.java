package com.pawasa.Controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClientController {
    @GetMapping("home")
    public String getHomepage(){
        return "pages/client/home";
    }
}
