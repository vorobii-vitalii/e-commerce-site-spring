package com.commerce.web.service.implementation;

import com.commerce.web.dto.ProductDTO;
import com.commerce.web.dto.ProductDetailsDTO;
import com.commerce.web.exceptions.ProductNotFoundException;
import com.commerce.web.exceptions.ProductsResultIsEmptyException;
import com.commerce.web.model.Product;
import com.commerce.web.model.Status;
import com.commerce.web.repository.ProductRepository;
import com.commerce.web.service.UserProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserProductServiceImpl implements UserProductService {

    private final ProductRepository productRepository;

    @Autowired
    public UserProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductDTO> getProducts() {

        List<ProductDTO> products = productRepository.findAll().stream()
                .filter(product -> product.getStatus() == Status.ACTIVE)
                .map(ProductDTO::fromProduct)
                .collect(Collectors.toList());

        if (products.isEmpty())
            throw new ProductsResultIsEmptyException("Products were not found");

        return products;
    }

    @Override
    public ProductDetailsDTO getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (!product.isPresent())
            throw new ProductNotFoundException("Product with id " + id + " was not found.");
        return ProductDetailsDTO.fromProduct(product.get());
    }
}
