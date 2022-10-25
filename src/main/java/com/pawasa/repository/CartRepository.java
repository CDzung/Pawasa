package com.pawasa.repository;

import com.pawasa.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUser_Id(Long id);
}
