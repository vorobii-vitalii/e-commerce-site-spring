package com.commerce.web.dto;

import com.commerce.web.model.Status;
import lombok.Data;

@Data
public class ReviewDTO {

    private Long id;

    private String content;

    private Integer rate;

    private UserDTO author;

    private ProductDTO product;

    private Status status;

}
