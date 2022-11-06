package com.pawasa.controller.seller;

import com.pawasa.model.Order;
import com.pawasa.model.OrderStatus;
import com.pawasa.repository.OrderRepository;
import com.pawasa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
@Transactional
public class SellerController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;


    @GetMapping("/seller")
    public String showSellerPage(Model model) {
        List<Order> orders = orderRepository.findAll();
        List<Order> finalOrders = new ArrayList<>();
        for(Order order: orders){
            Set<OrderStatus> orderStatuses = order.getOrderStatuses();
            OrderStatus recentOrderStatus = null;
            for(OrderStatus orderStatus: orderStatuses){
                if(recentOrderStatus == null){
                    recentOrderStatus = orderStatus;
                }else{
                    if(recentOrderStatus.getStatusDate().after(orderStatus.getStatusDate())){
                        recentOrderStatus = orderStatus;
                    }
                }
            }
            if(recentOrderStatus != null && recentOrderStatus.getOrderStatus().equals("Chờ xác nhận")){
                finalOrders.add(order);
            }
        }
        model.addAttribute("orders", finalOrders);
        return "pages/seller/dashboard";
    }
}
