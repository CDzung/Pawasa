package com.pawasa.service;

import com.pawasa.model.OrderStatus;
import com.pawasa.repository.OrderStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultOrderStatusService implements OrderStatusService {
    @Autowired
    private OrderStatusRepository orderStatusRepository;

    @Override
    public void addOrderStatus(OrderStatus orderStatus) {
        orderStatusRepository.save(orderStatus);
    }
}
