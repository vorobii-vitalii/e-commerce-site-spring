package com.commerce.web.dto;

import com.commerce.web.model.Product;
import com.commerce.web.model.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDTO {

    private Long id;

    private String name;

    private Double cost;

    private String description;

    @JsonIgnoreProperties("products")
    private UserDTO creator;

    @ToString.Exclude
    private List<ProductSpecificationDTO> productSpecifications;

    public static ProductDTO fromProduct( Product product ) {
        ProductDTO productDTO = new ProductDTO ();
        productDTO.setId ( product.getId () );
        productDTO.setName ( product.getName () );
        productDTO.setDescription ( product.getDescription () );
        productDTO.setCost ( product.getCost () );
        productDTO.setCreator ( UserDTO.fromUser ( product.getUser ()) );
        productDTO.setProductSpecifications (
                product.getProductSpecifications ().stream ()
                        .map ( productSpecification -> ProductSpecificationDTO.fromProductSpecification ( productSpecification ))
                        .collect ( Collectors.toList ( ) )
        );
        return productDTO;
    }

    public String toString() {
        return "ProductDTO #" + id + " name = " + name + " cost = " + cost;
    }
}
