package com.pawasa.controller;

import com.pawasa.exception.UserAlreadyExistsException;
import com.pawasa.model.User;
import com.pawasa.repository.UserRepository;
import com.pawasa.service.DefaultEmailService;
import com.pawasa.service.DefaultUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import java.util.Random;

@Controller
public class CustomerController{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DefaultEmailService emailService;

    @Autowired
    private DefaultUserService userService;

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid User user, BindingResult result, Model model) {
        if (result.hasFieldErrors("email")) {
            model.addAttribute("user", user);
            return "register";
        }
        try {
            userService.addUser(user);
        } catch (UserAlreadyExistsException e) {
            result.rejectValue("email", "user.email","An account already exists for this email.");
            model.addAttribute("user", user);
            return "register";
        }
        return "redirect:/";
    }

    @GetMapping("/otp")
    public String sendOtp(@Valid  User user, BindingResult result, Model model) {
        //validation
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "register";
        }
        if(userRepository.findByEmail(user.getEmail()) != null){
            result.rejectValue("email", "user.email","An account already exists for this email.");
            model.addAttribute("user", user);
            return "register";
        }

        //send otp
        try{
            Random rand = new Random();
            String otp = rand.nextInt(999999) + "";
            while (otp.length() < 6) {
                otp = "0" + otp;
            }
            String message = "<div style=\"font-family: Helvetica,Arial,sans-serif;min-width:1000px;overflow:auto;line-height:2\">\n" +
                    "  <div style=\"margin:50px auto;width:70%;padding:20px 0\">\n" +
                    "    <div style=\"border-bottom:1px solid #eee\">\n" +
                    "      <a href=\"\" style=\"font-size:1.4em;color: #C92127;text-decoration:none;font-weight:600\">Pawasa</a>\n" +
                    "    </div>\n" +
                    "    <p style=\"font-size:1.1em\">Hi,</p>\n" +
                    "    <p>Thank you for choosing Pawasa. Use the following OTP to complete your Sign Up procedures. OTP is valid for 5 minutes</p>\n" +
                    "    <h2 style=\"background: #C92127;margin: 0 auto;width: max-content;padding: 0 10px;color: #fff;border-radius: 4px;\">"+
                    otp +
                    "</h2>\n" +
                    "    <p style=\"font-size:0.9em;\">Regards,<br />Pawasa</p>\n" +
                    "    <hr style=\"border:none;border-top:1px solid #eee\" />\n" +
                    "    <div style=\"float:right;padding:8px 0;color:#aaa;font-size:0.8em;line-height:1;font-weight:300\">\n" +
                    "      <p>Pawasa Inc</p>\n" +
                    "      <p>1600 Amphitheatre Parkway</p>\n" +
                    "      <p>California</p>\n" +
                    "    </div>\n" +
                    "  </div>\n" +
                    "</div>";

            emailService.sendEmail(user.getEmail(), "Pawasa - Email Verification", message);
            model.addAttribute("user", user);
            return "register";
        } catch (Exception e) {
            return "error";
        }
    }
}