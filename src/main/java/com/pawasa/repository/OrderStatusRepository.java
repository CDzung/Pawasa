package com.pawasa.repository;

import com.pawasa.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderStatusRepository extends JpaRepository<OrderStatus, Long> {
    List<OrderStatus> findByOrder_OrderIdOrderByIdDesc(Long orderId);

}