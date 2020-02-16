package com.commerce.web.dto;

import com.commerce.web.model.Product;
import com.commerce.web.model.Status;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
public class AddProductRequestDTO {

    @NotBlank(message = "Name is mandatory")
    @Column(name="name")
    private String name;

    @Positive(message = "Cost of product is required")
    @Column(name="cost")
    private Double cost;

    public Product toProduct() {
        Product product = new Product ();
        product.setName ( name );
        product.setCost ( cost );
        product.setStatus ( Status.ACTIVE );
        return product;
    }

}
