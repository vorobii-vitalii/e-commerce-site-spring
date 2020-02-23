package com.commerce.web.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "products")
public class Product extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ToString.Exclude
    @OneToMany(mappedBy = "product")
    private List<ProductSpecification> productSpecifications;

    @Column(name = "cost")
    private Double cost;

    @ManyToOne
    @JoinColumn(name = "user_id",
            referencedColumnName = "id"
    )
    private User user;

    @OneToMany(mappedBy = "product")
    private List<Review> reviews;

}
