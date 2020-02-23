package com.commerce.web.rest;

import com.commerce.web.dto.AddProductRequestDTO;
import com.commerce.web.dto.AdminProductDTO;
import com.commerce.web.dto.EditProductRequestDTO;
import com.commerce.web.exceptions.ProductNotFoundException;
import com.commerce.web.exceptions.ProductsResultIsEmptyException;
import com.commerce.web.exceptions.SpecificationNotFoundByNameException;
import com.commerce.web.exceptions.UserWasNotFoundByEmailException;
import com.commerce.web.model.User;
import com.commerce.web.service.ProductService;
import com.commerce.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/v1/admin/products")
public class AdminProductsRestController {

    private final UserService userService;
    private final ProductService productService;

    @Autowired
    public AdminProductsRestController(UserService userService, ProductService productService) {
        this.userService = userService;
        this.productService = productService;
    }


    @GetMapping(value = "")
    public ResponseEntity<List<AdminProductDTO>> getProducts() throws ProductsResultIsEmptyException {
        return new ResponseEntity<>(productService.getAll()
                .stream()
                .map(AdminProductDTO::fromProduct)
                .collect(Collectors.toList()),
                HttpStatus.OK);
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<AdminProductDTO> getProductById(@PathVariable @Positive Long id) throws ProductNotFoundException {
        return new ResponseEntity<>(AdminProductDTO.fromProduct(productService.getById(id)), HttpStatus.OK);
    }


    @PostMapping(value = "/add")
    public ResponseEntity<AdminProductDTO> addProduct(@Valid @RequestBody AddProductRequestDTO addProductRequestDTO, Authentication authentication) throws UserWasNotFoundByEmailException, SpecificationNotFoundByNameException {
        User authorOfProduct = userService.findByEmail(authentication.getName());
        return new ResponseEntity<>(productService.addProduct(addProductRequestDTO, authorOfProduct), HttpStatus.OK);
    }


    @PostMapping(value = "/edit/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void editProduct(@PathVariable @Positive Long id, @Valid @RequestBody EditProductRequestDTO editProductRequestDTO) throws ProductNotFoundException, SpecificationNotFoundByNameException {
        productService.editProductById(id, editProductRequestDTO);
    }


    @PostMapping(value = "/delete/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteProduct(@PathVariable @Positive Long id) throws ProductNotFoundException {
        productService.deleteById(id);
    }


}
