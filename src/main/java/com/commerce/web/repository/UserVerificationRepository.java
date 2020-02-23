package com.commerce.web.repository;

import com.commerce.web.model.UserVerification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserVerificationRepository extends JpaRepository<UserVerification, Long> {
    UserVerification findByToken(String token);
}
