package com.pawasa.controller.client;

import com.pawasa.model.Notification;
import com.pawasa.model.Order;
import com.pawasa.model.User;
import com.pawasa.repository.NotificationRepository;
import com.pawasa.repository.OrderRepository;
import com.pawasa.repository.RoleRepository;
import com.pawasa.repository.UserRepository;
import com.pawasa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

@Controller
public class CustomerInfomationController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserService userService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @GetMapping("/user/account/profile")
    public String showProfile(Model model, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(user.getDob());
            int day = cal.get(Calendar.DAY_OF_MONTH);
            int month = cal.get(Calendar.MONTH);
            int year = cal.get(Calendar.YEAR);
            model.addAttribute("day", day);
            model.addAttribute("month", month);
            model.addAttribute("year", year);
        }
        return "pages/client/Profile";
    }

    @PostMapping("/user/account/profile")
    public String updateProfile(@RequestParam(name = "lastname") String lastname, @RequestParam(name = "firstname") String firstname,
                                @RequestParam(name = "telephone") String phone, @RequestParam(name = "email") String email, @RequestParam(name = "gender-radio") String gender,
                                @RequestParam(name = "day") String day, @RequestParam(name = "month") String month, @RequestParam(name = "year") String year,
                                @RequestParam(name = "current_password") String oldPass, @RequestParam(name = "password") String newPass, @RequestParam(name = "confirmation") String confirm
            , HttpServletRequest request, HttpServletResponse response, Model model) throws ParseException {
        HttpSession session = request.getSession();
        User u = (User) session.getAttribute("user");
        User user = userRepository.findById(u.getId()).orElse(null);
        user.setFirstName(firstname);
        user.setLastName(lastname);
        user.setPhoneNumber(phone);
        user.setEmail(email);
        String date_String = year + "-" + day + "-" + month;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-dd-MM");
        Date date = formatter.parse(date_String);
        user.setDob(date);
        if (oldPass != null) {
            if (newPass.equals(confirm)) {
                user.setPassword(BCrypt.hashpw(newPass, BCrypt.gensalt()));
            } else {
                model.addAttribute("error", "Password and Re-Password not match");
                return "pages/client/Profile";
            }
        }
        u.setRole(roleRepository.findByRoleName("Customer"));
        userService.addUser(u);
        model.addAttribute("report", "Save successful!!");
        return "pages/client/Profile";
    }

    @GetMapping("/user/account/history")
    public String getHistoryOrder(@RequestParam(name = "status") String status, HttpServletRequest request, HttpServletResponse response, Model model) {
        HttpSession session = request.getSession();
        User u = (User) session.getAttribute("user");
        if (u == null) {
            return "redirect:/login";
        }
        Set<Order> list_order;
        int total = orderRepository.findByUser_Id(u.getId()).size();
        int pending = orderRepository.findByUser_IdAndOrderStatuses_Id(u.getId(), 1L).size();
        int process = orderRepository.findByUser_IdAndOrderStatuses_Id(u.getId(), 2L).size();
        int complete = orderRepository.findByUser_IdAndOrderStatuses_Id(u.getId(), 3L).size();
        int cancel = orderRepository.findByUser_IdAndOrderStatuses_Id(u.getId(), 4L).size();
        if (status == null || status.equals("All")) {
            list_order = orderRepository.findByUser_Id(u.getId());
        } else if (status.equals("pending")) {
            list_order = orderRepository.findByUser_IdAndOrderStatuses_Id(u.getId(), 1L);
        } else if (status.equals("processing")) {
            list_order = orderRepository.findByUser_IdAndOrderStatuses_Id(u.getId(), 2L);
        } else if (status.equals("complete")) {
            list_order = orderRepository.findByUser_IdAndOrderStatuses_Id(u.getId(), 3L);
        } else {
            list_order = orderRepository.findByUser_IdAndOrderStatuses_Id(u.getId(), 4L);
        }
        model.addAttribute("list", list_order);
        model.addAttribute("total", total);
        model.addAttribute("pending", pending);
        model.addAttribute("process", process);
        model.addAttribute("complete", complete);
        model.addAttribute("cancel", cancel);
        return "pages/client/Order";
    }

    @GetMapping("/user/account/notify")
    public String getNotification(@RequestParam(name = "status") String status, HttpServletRequest request, HttpServletResponse response, Model model) {
        HttpSession session = request.getSession();
        User u = (User) session.getAttribute("user");
        if (u == null) {
            return "redirect:/login";
        }
        Set<Notification> list_notify = notificationRepository.findByUser_Id(u.getId());
        model.addAttribute("list_noti",list_notify);
        return "pages/client/Notification";
    }
}
