package com.commerce.web.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "reviews")
public class Review extends BaseEntity {

    @Column(name = "content")
    private String content;

    @Column(name = "rate")
    private Integer rate;

    @ManyToOne
    @JoinColumn(name = "user_id",
            referencedColumnName = "id",
            nullable = false
    )
    private User author;


    @ManyToOne
    @JoinColumn(name = "product_id",
            referencedColumnName = "id",
            nullable = false
    )
    private Product product;


    @ManyToOne
    @JoinColumn(
            name = "parent_id",
            referencedColumnName = "id"
    )
    private Review parent;


    @OneToMany(mappedBy = "parent")
    private List<Review> children;

}
