package com.pawasa.repository;

import com.pawasa.model.Product;
import com.pawasa.model.Review;
import com.pawasa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Review findByUserAndProduct(User user, Product product);

    List<Review> findByProduct(Product product);

    int countAllByProduct(Product product);

    List<Review> findAllByProduct(Product product);
}
