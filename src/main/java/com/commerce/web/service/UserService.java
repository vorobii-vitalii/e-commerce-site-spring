package com.commerce.web.service;

import com.commerce.web.exceptions.UserIsAlreadyVerifiedException;
import com.commerce.web.exceptions.UserWasNotFoundByEmailException;
import com.commerce.web.exceptions.VerificationTokenExpiredException;
import com.commerce.web.exceptions.VerificationTokenHasNotMatchedException;
import com.commerce.web.model.User;

import java.util.List;

public interface UserService {

    User register( User user);

    User verifyByToken(String token) throws VerificationTokenExpiredException, VerificationTokenHasNotMatchedException;

    List<User> getAll();

    User findByEmail(String email);

    User findById(Long id);

    String regenerateToken(String email) throws UserWasNotFoundByEmailException, UserIsAlreadyVerifiedException;

    void delete(Long id);
}
