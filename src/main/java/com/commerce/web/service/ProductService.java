package com.commerce.web.service;

import com.commerce.web.dto.AddProductRequestDTO;
import com.commerce.web.dto.EditProductRequestDTO;
import com.commerce.web.exceptions.ProductNotFoundException;
import com.commerce.web.exceptions.ProductsResultIsEmptyException;
import com.commerce.web.exceptions.SpecificationNotFoundByNameException;
import com.commerce.web.model.Product;
import com.commerce.web.model.User;

import java.util.List;

public interface ProductService {

    void addProduct( AddProductRequestDTO addProductRequestDTO , User author ) throws SpecificationNotFoundByNameException;

    void editProductById ( Long id, EditProductRequestDTO editProductRequestDTO ) throws ProductNotFoundException, SpecificationNotFoundByNameException;

    List<Product> getAll() throws ProductsResultIsEmptyException;

    Product getById(Long id) throws ProductNotFoundException;

    void deleteById ( Long id ) throws ProductNotFoundException;

}
