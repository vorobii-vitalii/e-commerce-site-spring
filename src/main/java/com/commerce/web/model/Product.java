package com.commerce.web.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
@Entity
@Table(name="products")
public class Product extends BaseEntity {

    @NotBlank(message = "Name is mandatory")
    @Column(name="name")
    private String name;

    @Positive(message = "Cost of product is required")
    @Column(name="cost")
    private Double cost;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = true)
    private User user;

}
