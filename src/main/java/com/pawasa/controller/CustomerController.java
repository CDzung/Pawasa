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
import java.util.Date;
import java.util.Random;

@Controller
public class CustomerController{
    private final static String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DefaultEmailService emailService;

    @Autowired
    private DefaultUserService userService;

    @GetMapping("/signup")
    public String register(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("form", "signup");
        return "register";
    }

    @PostMapping("/signup")
    public String register(@Valid User user, BindingResult result, Model model) {
        if (result.hasFieldErrors("email") || result.hasFieldErrors("password") || result.hasFieldErrors("otp")) {
//            model.addAttribute("user", user);
            return "register";
        }

        try {
            userService.addUser(user);
        } catch (UserAlreadyExistsException e) {
            result.rejectValue("email", "user.email", e.getMessage());
//            model.addAttribute("user", user);
            return "register";
        } catch (IllegalArgumentException e) {
            result.rejectValue("password", "user.password", e.getMessage());
            return "register";
        }

        return "redirect:/";
    }

    @PostMapping("/signup/otp")
    public String sendOtp(@Valid  User user, BindingResult result, Model model, @RequestParam("cf_password") String cfPassword) {

        if(user.getPassword()!="" && !user.getPassword().matches(passwordRegex)) {
            result.rejectValue("password", "user.password", "Password must contain at least one digit, one lowercase, one uppercase, one special character and must be at least 8 characters long.");
        }

        if (result.hasFieldErrors("email") || result.hasFieldErrors("password")) {
            if(cfPassword.isEmpty())
                model.addAttribute("cf_password", "Confirm password is required");
            else
                if(!cfPassword.equals(user.getPassword()))
                    model.addAttribute("cf_password", "Confirm password is not match");
             return "register";
        }

        if(cfPassword.isEmpty()){
            model.addAttribute("cf_password", "Confirm password is required");
            return "register";
        }
        else if(!cfPassword.equals(user.getPassword())) {
            model.addAttribute("cf_password", "Confirm password is not match");
            return "register";
        }

        if(userRepository.findByEmail(user.getEmail()) != null){
            result.rejectValue("email", "user.email","An account already exists for this email.");
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

            //emailService.sendEmail(user.getEmail(), "Pawasa - Email Verification", message);
            user.setOtp(otp);
            user.setOtpRequestedTime(new Date());
            return "register";
        } catch (Exception e) {
            return "error";
        }
    }
}