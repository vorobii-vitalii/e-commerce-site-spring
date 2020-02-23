package com.commerce.web.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "specifications")
public class Specification extends BaseEntity {

    @NotEmpty(message = "Specification name cannot be empty")
    @Column(name = "name", unique = true)
    private String name;

    @NotEmpty(message = "Specification name cannot be empty")
    @Column(name = "formatted_name")
    private String formattedName;

}
