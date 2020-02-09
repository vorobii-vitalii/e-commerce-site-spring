package com.commerce.web.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.List;

@Entity
@Table(name="users")
@Data
public class User extends BaseEntity {

    @Email
    @Column(name="email")
    private String email;

    @Column(name="password")
    private String password;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="user_roles",
            joinColumns = {
                    @JoinColumn(
                            name="user_id",
                            referencedColumnName = "id"
                    ),
            },
            inverseJoinColumns = {
                    @JoinColumn(
                            name="role_id",
                            referencedColumnName = "id"
                    )
            }
    )
    private List<Role> roles;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="user_verification_id", referencedColumnName = "id")
    private UserVerification userVerification;


    public String toString() {
        return "User #" + getId ();
    }

}
