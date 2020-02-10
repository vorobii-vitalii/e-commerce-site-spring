package com.commerce.web.repository;

import com.commerce.web.model.UserPasswordReset;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPasswordResetRepository extends JpaRepository<UserPasswordReset,Long> {
    UserPasswordReset findByToken(String token);
}
