package com.commerce.web.repository;

import com.commerce.web.model.Product;
import com.commerce.web.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByProductAndParentNull(Product product);

}
