package com.commerce.web.dto;

import com.commerce.web.model.Status;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReviewDTO {

    private Long id;

    private String content;

    private Integer rate;

    private UserDTO author;

    private ProductDTO product;

    private Status status;

}
