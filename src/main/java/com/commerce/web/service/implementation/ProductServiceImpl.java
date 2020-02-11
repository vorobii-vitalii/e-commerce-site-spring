package com.commerce.web.service.implementation;

import com.commerce.web.exceptions.ProductNotFoundException;
import com.commerce.web.exceptions.ProductsResultIsEmptyException;
import com.commerce.web.model.Product;
import com.commerce.web.model.Status;
import com.commerce.web.repository.ProductRepository;
import com.commerce.web.repository.UserRepository;
import com.commerce.web.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository,UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Product> getAll () throws ProductsResultIsEmptyException {

        List<Product> fetchedProducts = productRepository.findAll ();

        if (fetchedProducts.isEmpty ())
            throw new ProductsResultIsEmptyException ( "Products were not found" );

        return fetchedProducts;
    }

    @Override
    public Product getById ( Long id ) throws ProductNotFoundException {

        Product foundProduct = productRepository.findById ( id ).orElse ( null );

        if (foundProduct == null)
            throw new ProductNotFoundException ( "Product with id " + id + " was not found" );

        return foundProduct;
    }

    @Override
    public void deleteById ( Long id ) throws ProductNotFoundException {

        Product foundProduct = productRepository.findById ( id ).orElse ( null );

        if (foundProduct == null)
            throw new ProductNotFoundException ( "Product with id " + id + " was not found" );

        foundProduct.setStatus ( Status.DELETED );
    }

}
