package com.pawasa.repository;

import com.pawasa.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Set<Order> findByUser_Id(Long id);

    Set<Order> findByShipper_Id(Long id);




}
