package com.pawasa.controller.shipper;

import com.pawasa.model.Order;
import com.pawasa.model.OrderStatus;
import com.pawasa.model.User;
import com.pawasa.repository.OrderRepository;
import com.pawasa.repository.UserRepository;
import com.pawasa.service.OrderStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
public class ShipperController {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderStatusService orderStatusService;

    @GetMapping("/shipper")
    public String getDashBoard(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User u = userRepository.findByEmail(email);
        List<Order> set = orderRepository.findAll();
        List<Order> list = new ArrayList<>();
        for (Order i : set) {
            if ((i.getOrderStatuses().size() == 1 && i.getUser() == null) ||
                    (i.getOrderStatuses().size() == 2 && i.getShipper().equals(u))) {
                for (OrderStatus j : i.getOrderStatuses()) {
                    if (j.getOrderStatus().equals("Đã xác nhận")) {
                        list.add(i);
                    }
                }
            }
        }
        double sum = set.stream().filter(order -> order.getOrderStatuses().size() == 3 && order.getUser().equals(u))
                .map(order -> order.getTotalPrice().doubleValue()).reduce(0.0, (aDouble, aDouble2) -> aDouble + aDouble2);
        double count = set.stream().filter(order -> order.getOrderStatuses().size() == 3 && order.getUser().equals(u)).count();
        model.addAttribute("order_set", list);
        model.addAttribute("sum", sum);
        model.addAttribute("count", count);
        return "pages/shipper/dashboard";
    }

    @GetMapping("/shipper/history")
    public String getHistory(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User u = userRepository.findByEmail(email);
        Set<Order> set = orderRepository.findByShipper_Id(u.getId());
        List<Order> list = new ArrayList<>();
        for (Order i : set) {
            if (i.getOrderStatuses().size() == 3) {
                list.add(i);
            }
        }
        model.addAttribute("order_set", list);
        return "pages/shipper/history";
    }

    @GetMapping("/shipper/receiveOrder")
    public void receiveOrder(@RequestParam(name = "id") Optional<Long> id) {
        Long Orderid = id.get();
        Order order = orderRepository.findById(Orderid).get();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User u = userRepository.findByEmail(email);
        order.setUser(u);
        orderRepository.save(order);
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderStatus("Đơn hàng đang giao");
        orderStatus.setOrder(order);
        orderStatus.setStatusDate(new Date());
        orderStatusService.addOrderStatus(orderStatus);
    }

    @GetMapping("/shipper/completeOrder")
    public void completeOrder(@RequestParam(name = "id") Optional<Long> id, @RequestParam(name = "message") Optional<String> message) {
        Long Orderid = id.get();
        Order order = orderRepository.findById(Orderid).get();
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderStatus(message.get());
        orderStatus.setOrder(order);
        orderStatus.setStatusDate(new Date());
        orderStatusService.addOrderStatus(orderStatus);
    }

    @GetMapping("/shipper/detail")
    public String getOrder(@RequestParam(name = "id") Optional<Long> id, Model model, HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        Long Orderid = id.get();
        Order order = orderRepository.findById(Orderid).get();
        model.addAttribute("order",order);
        model.addAttribute("url",referer);
        return "pages/shipper/DetailOrder";
    }
}
