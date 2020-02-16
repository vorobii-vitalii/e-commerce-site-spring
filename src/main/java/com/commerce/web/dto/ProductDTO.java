package com.commerce.web.dto;

import com.commerce.web.model.Product;
import com.commerce.web.model.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDTO {

    private Long id;

    private String name;

    private Double cost;

    @JsonIgnoreProperties("products")
    private UserDTO creator;

    public static ProductDTO fromProduct( Product product ) {
        ProductDTO productDTO = new ProductDTO ();
        productDTO.setName ( product.getName () );
        productDTO.setCost ( product.getCost () );
        productDTO.setCreator ( UserDTO.fromUser ( product.getUser ()) );
        productDTO.setId ( product.getId () );
        return productDTO;
    }
}
