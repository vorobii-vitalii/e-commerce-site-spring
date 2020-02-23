package com.commerce.web.service;

import com.commerce.web.dto.ProductDTO;
import com.commerce.web.dto.ProductDetailsDTO;
import com.commerce.web.exceptions.ProductNotFoundException;
import com.commerce.web.exceptions.ProductsResultIsEmptyException;

import java.util.List;

public interface UserProductService {

    List<ProductDTO> getProducts();

    ProductDetailsDTO getProductById(Long id);

}
