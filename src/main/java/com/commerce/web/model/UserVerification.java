package com.commerce.web.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "users_verification")
public class UserVerification extends BaseEntity {

    @Column(name = "token")
    private String token;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private User user;

}
