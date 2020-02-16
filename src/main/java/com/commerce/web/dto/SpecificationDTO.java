package com.commerce.web.dto;

import com.commerce.web.model.Specification;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class SpecificationDTO {

    @NotEmpty(message = "Name is mandatory")
    private String name;

    @NotEmpty(message = "Formatted name is mandatory")
    private String formattedName;

    public Specification toSpecification() {
        Specification specification = new Specification ();
        specification.setName ( name );
        specification.setFormattedName ( formattedName );
        return specification;
    }

    public static SpecificationDTO fromSpecification(Specification specification) {
        SpecificationDTO specificationDTO = new SpecificationDTO ();
        specificationDTO.setName ( specification.getName () );
        specificationDTO.setFormattedName ( specificationDTO.getFormattedName () );
        return specificationDTO;
    }

}
