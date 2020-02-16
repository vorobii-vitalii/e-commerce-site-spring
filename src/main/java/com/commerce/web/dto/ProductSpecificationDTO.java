package com.commerce.web.dto;


import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class ProductSpecificationDTO {

    @NotEmpty(message = "Name of specification is required")
    private String name;

    @NotEmpty(message = "Value of specification is required")
    private String value;

}
