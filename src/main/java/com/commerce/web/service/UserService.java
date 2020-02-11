package com.commerce.web.service;

import com.commerce.web.dto.UserDTO;
import com.commerce.web.exceptions.*;
import com.commerce.web.model.User;

import java.util.List;

public interface UserService {

    User register( User user);

    List<User> getAll() throws UsersResultIsEmptyException;

    User findByEmail(String email) throws UserWasNotFoundByEmailException;

    User findById(Long id) throws UserWasNotFoundException, UserIsDeletedException;

    User verifyByToken(String token) throws VerificationTokenExpiredException, VerificationTokenHasNotMatchedException;

    String regenerateVerificationToken ( String email) throws UserWasNotFoundByEmailException, UserIsAlreadyVerifiedException;

    String resetPasswordByEmail(String email) throws UserWasNotFoundByEmailException, UserNotActiveException;

    void changePasswordByToken(String token,String password) throws PasswordResetTokenIsNotValid, VerificationTokenExpiredException, PasswordResetTokenIsNotActive, PasswordResetTokenHasExpired;

    void changeUser( Long id, UserDTO userDTO ) throws UserWasNotFoundException, RolesAreInvalidException, AdminIsImmutableException;

    void deleteUser ( Long id) throws UserWasNotFoundException, AdminIsImmutableException;
}
