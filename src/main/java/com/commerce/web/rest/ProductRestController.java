package com.commerce.web.rest;

import com.commerce.web.dto.ProductDTO;
import com.commerce.web.exceptions.ProductsResultIsEmptyException;
import com.commerce.web.service.ProductService;
import com.commerce.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping(value = "/")
    public ResponseEntity<List<ProductDTO>> getProducts() throws ProductsResultIsEmptyException {
        return new ResponseEntity<>(productService.getAll ()
                .stream ()
                .map ( product -> ProductDTO.fromProduct ( product ) )
                .collect ( Collectors.toList ( )),
                HttpStatus.OK);
    }

}
