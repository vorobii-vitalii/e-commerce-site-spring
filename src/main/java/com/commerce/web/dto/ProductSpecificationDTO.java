package com.commerce.web.dto;

import com.commerce.web.model.ProductSpecification;
import lombok.Data;

@Data
public class ProductSpecificationDTO {

    private String name;

    private String formattedName;

    private String value;

    public static ProductSpecificationDTO  fromProductSpecification( ProductSpecification productSpecification ) {

        ProductSpecificationDTO productSpecificationDTO = new ProductSpecificationDTO ();

        productSpecificationDTO.setName ( productSpecification.getSpecification ().getName () );
        productSpecificationDTO.setFormattedName ( productSpecification.getSpecification ().getFormattedName () );
        productSpecificationDTO.setValue ( productSpecification.getValue () );

        return productSpecificationDTO;
    }

}
