package com.commerce.web.dto;

import com.commerce.web.model.Product;
import com.commerce.web.model.Status;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDetailsDTO {

    private Long id;

    private String name;

    private Double cost;

    private String description;

    @ToString.Exclude
    private List<ProductSpecificationDTO> productSpecifications;

    public static ProductDetailsDTO fromProduct(Product product) {
        ProductDetailsDTO productDetailsDTO = new ProductDetailsDTO();
        productDetailsDTO.setId(product.getId());
        productDetailsDTO.setName(product.getName());
        productDetailsDTO.setCost(product.getCost());
        productDetailsDTO.setDescription(product.getDescription());
        productDetailsDTO.setProductSpecifications(product.getProductSpecifications().stream()
                .filter(productSpecification -> productSpecification.getSpecification().getStatus() == Status.ACTIVE)
                .map(ProductSpecificationDTO::fromProductSpecification)
                .collect(Collectors.toList()));
        return productDetailsDTO;
    }

}
