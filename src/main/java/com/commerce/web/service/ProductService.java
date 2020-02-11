package com.commerce.web.service;

import com.commerce.web.exceptions.ProductNotFoundException;
import com.commerce.web.exceptions.ProductsResultIsEmptyException;
import com.commerce.web.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> getAll() throws ProductsResultIsEmptyException;

    Product getById(Long id) throws ProductNotFoundException;

    void deleteById ( Long id ) throws ProductNotFoundException;

}
