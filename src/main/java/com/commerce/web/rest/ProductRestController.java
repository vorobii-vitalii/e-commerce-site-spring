package com.commerce.web.rest;

import com.commerce.web.dto.ProductDTO;
import com.commerce.web.dto.ProductDetailsDTO;
import com.commerce.web.exceptions.ProductNotFoundException;
import com.commerce.web.exceptions.ProductsResultIsEmptyException;
import com.commerce.web.model.Product;
import com.commerce.web.model.Status;
import com.commerce.web.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/v1/products")
public class ProductRestController {

    private final ProductService productService;

    @Autowired
    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = "")
    public ResponseEntity<List<ProductDTO>> getProducts() throws ProductsResultIsEmptyException {
        return new ResponseEntity<>(productService.getAll()
                .stream()
                .filter(product -> product.getStatus() == Status.ACTIVE)
                .map(ProductDTO::fromProduct)
                .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductDetailsDTO> getProductDetails(@PathVariable @Positive Long id) throws ProductNotFoundException {
        Product foundProduct = productService.getById(id);
        if (foundProduct.getStatus() != Status.ACTIVE)
            throw new ProductNotFoundException("Product has been deleted");
        return new ResponseEntity<>(ProductDetailsDTO.fromProduct(foundProduct), HttpStatus.OK);
    }

}
