package com.commerce.web.service;

import com.commerce.web.dto.AddProductRequestDTO;
import com.commerce.web.dto.AdminProductDTO;
import com.commerce.web.dto.EditProductRequestDTO;
import com.commerce.web.exceptions.ProductNotFoundException;
import com.commerce.web.exceptions.ProductsResultIsEmptyException;
import com.commerce.web.exceptions.SpecificationNotFoundByNameException;
import com.commerce.web.model.Product;
import com.commerce.web.model.User;

import java.util.List;

public interface ProductService {

    AdminProductDTO addProduct(AddProductRequestDTO addProductRequestDTO, User author);

    void editProductById(Long id, EditProductRequestDTO editProductRequestDTO);

    List<Product> getAll();

    Product getById(Long id);

    void deleteById(Long id);

}
