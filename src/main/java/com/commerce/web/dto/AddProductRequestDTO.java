package com.commerce.web.dto;

import com.commerce.web.model.Product;
import com.commerce.web.model.Status;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.List;

@Data
public class AddProductRequestDTO {

    @NotBlank(message = "Name is mandatory")
    private String name;

    @Positive(message = "Cost of product is required")
    private Double cost;

    private String description;

    private List<AddProductSpecificationDTO> productSpecifications;

    public Product toProduct() {
        Product product = new Product();
        product.setName(name);
        product.setCost(cost);
        product.setDescription(description);
        product.setStatus(Status.ACTIVE);
        return product;
    }

}
