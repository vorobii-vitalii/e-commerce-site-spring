package com.commerce.web.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Data
@Entity
@Table(name="product_specifications")
public class ProductSpecification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(
            name = "product_id",
            referencedColumnName = "id",
            nullable = false
    )
    private Product product;


    @ManyToOne
    @JoinColumn(
            name = "specification_id",
            referencedColumnName = "id",
            nullable = false
    )
    private Specification specification;

    @Column(name = "value")
    @NotEmpty(message = "Value is mandatory")
    private String value;

}
