package com.pawasa.repository;

import com.pawasa.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Set<Order> findByUser_Id(Long id);

    Order findById(long id);
    Set<Order> findByUser_IdAndOrderStatuses_Id(Long id, Long id1);

}
