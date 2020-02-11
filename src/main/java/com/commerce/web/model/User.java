package com.commerce.web.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="users")
@Data
public class User extends BaseEntity {

    @Email
    @Column(name="email")
    @NotBlank(message = "Email is mandatory")
    private String email;

    @NotBlank(message = "Password is mandatory")
    @Column(name="password")
    private String password;

    @NotBlank(message = "First name is mandatory")
    @Column(name="first_name")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
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


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="user_password_reset_id", referencedColumnName = "id")
    private UserPasswordReset userPasswordReset;

    @OneToMany(mappedBy = "user")
    private Set<Product> products;

    public String toString() {
        return "User #" + getId ();
    }

}
