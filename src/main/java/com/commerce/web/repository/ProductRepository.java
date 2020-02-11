package com.commerce.web.repository;

import com.commerce.web.model.Product;
import com.commerce.web.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {

    Product findByName(String name);

    List<Product> findByUser( User user);

}
