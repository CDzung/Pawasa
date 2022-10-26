package com.pawasa.controller.admin;

import com.pawasa.model.Role;
import com.pawasa.model.User;
import com.pawasa.repository.RoleRepository;
import com.pawasa.repository.UserRepository;
import com.pawasa.service.DefaultUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@Transactional
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private DefaultUserService userService;

    @GetMapping("/admin/dashboard")
    public String index(Model model, Principal principal) {
        User user = userRepository.findByEmail(principal.getName());
        List<User> admins = userRepository.findAllByRole(roleRepository.findByRoleName("Admin"));
        List<User> users = userRepository.findAll();
        users.removeAll(admins);
        model.addAttribute("user", user);
        model.addAttribute("users", users);
        return "pages/admin/dashboard";
    }

    @GetMapping("/admin/create-account")
    public String show(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        List<Role> roles = roleRepository.findAll();
        roles.remove(roleRepository.findByRoleName("Admin"));
        roles.remove(roleRepository.findByRoleName("Customer"));
        model.addAttribute("roles", roles);
        return "pages/admin/create_account";
    }

    @PostMapping("/admin/create-account")
    public String create(User user, @RequestParam String role_id, @RequestParam(name = "date") String dob) throws ParseException {
        Role role = roleRepository.findById(Long.parseLong(role_id));
        Date date=new SimpleDateFormat("yyyy-MM-dd").parse(dob);
        user.setDob(date);
        user.setRole(role);
        user.setPassword(role.getRoleName() + "123!");
        user.setActive(true);
        userService.addUser(user);
        return "pages/admin/create_account";
    }
}
