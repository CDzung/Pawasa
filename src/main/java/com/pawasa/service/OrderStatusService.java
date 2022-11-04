package com.pawasa.service;

import com.pawasa.model.OrderStatus;
import org.springframework.stereotype.Service;

@Service("")
public interface OrderStatusService {
    void addOrderStatus(OrderStatus orderStatus);
}
