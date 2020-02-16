package com.commerce.web.dto;

import com.commerce.web.model.Product;
import com.commerce.web.model.Status;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.List;

@Data
public class EditProductRequestDTO {

    private String name;

    private Double cost;

    private String description;

    private Status status;

    private List<ProductSpecificationDTO> productSpecifications;

    public Product toProduct() {
        Product product = new Product ();
        product.setName ( name );
        product.setCost ( cost );
        product.setDescription ( description );
        product.setStatus ( status );
        return product;
    }

}
