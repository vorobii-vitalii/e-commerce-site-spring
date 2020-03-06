package com.commerce.web.dto;

import com.commerce.web.model.Product;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDTO {

    private Long id;

    private String name;

    private Double cost;

    private String description;

    public static ProductDTO fromProduct(Product product) {

        ProductDTO productDTO = new ProductDTO();

        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setCost(product.getCost());
        productDTO.setDescription(product.getDescription());

        return productDTO;
    }

    public Product toProduct() {

        Product product = new Product();

        product.setId(id);
        product.setName(name);
        product.setCost(cost);
        product.setDescription(description);

        return product;
    }

}
