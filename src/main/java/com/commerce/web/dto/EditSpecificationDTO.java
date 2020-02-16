package com.commerce.web.dto;

import com.commerce.web.model.Status;
import lombok.Data;

@Data
public class EditSpecificationDTO {

    private String name;

    private String formattedName;

    private Status status;

}
