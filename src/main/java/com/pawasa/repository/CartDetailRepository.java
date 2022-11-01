package com.pawasa.repository;

import com.pawasa.model.Cart;
import com.pawasa.model.CartDetail;
import com.pawasa.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail, Long> {

    CartDetail findByCartAndProduct(Cart cart, Product product);

    List<CartDetail> findAllByCart(Cart cart);

    CartDetail findById(long id);
}
