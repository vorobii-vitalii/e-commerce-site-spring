package com.commerce.web.service;

import com.commerce.web.exceptions.*;
import com.commerce.web.model.User;

import java.util.List;

public interface UserService {

    User register( User user);

    List<User> getAll();

    User findByEmail(String email);

    User findById(Long id);

    User verifyByToken(String token) throws VerificationTokenExpiredException, VerificationTokenHasNotMatchedException;

    String regenerateVerificationToken ( String email) throws UserWasNotFoundByEmailException, UserIsAlreadyVerifiedException;

    String resetPasswordByEmail(String email) throws UserWasNotFoundByEmailException, UserNotActiveException;

    void changePasswordByToken(String token,String password) throws PasswordResetTokenIsNotValid, VerificationTokenExpiredException, PasswordResetTokenIsNotActive, PasswordResetTokenHasExpired;

    void delete(Long id);
}
