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
public class AdminProductDTO {

    private Long id;

    private String name;

    private Double cost;

    private String description;

    private Status status;

    @JsonIgnoreProperties("products")
    private UserDTO creator;

    @ToString.Exclude
    private List<ProductSpecificationDTO> productSpecifications;

    public static AdminProductDTO fromProduct(Product product) {
        AdminProductDTO adminProductDTO = new AdminProductDTO();
        adminProductDTO.setId(product.getId());
        adminProductDTO.setName(product.getName());
        adminProductDTO.setDescription(product.getDescription());
        adminProductDTO.setCost(product.getCost());
        adminProductDTO.setStatus(product.getStatus());
        adminProductDTO.setCreator(UserDTO.fromUser(product.getUser()));
        adminProductDTO.setProductSpecifications(
                product.getProductSpecifications().stream()
                        .map(productSpecification -> ProductSpecificationDTO.fromProductSpecification(productSpecification))
                        .collect(Collectors.toList())
        );
        return adminProductDTO;
    }

    public String toString() {
        return "ProductDTO #" + id + " name = " + name + " cost = " + cost;
    }
}
