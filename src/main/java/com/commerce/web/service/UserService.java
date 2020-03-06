package com.commerce.web.service;

import com.commerce.web.dto.UserDTO;
import com.commerce.web.model.User;

import java.util.List;

public interface UserService {

    User register(User user);

    List<User> getAll();

    User findByEmail(String email);

    User findById(Long id);

    User verifyByToken(String token);

    String regenerateVerificationToken(String email);

    String resetPasswordByEmail(String email);

    void changePasswordByToken(String token, String password);

    void changeUser(Long id, UserDTO userDTO);

    void deleteUser(Long id);

    void editProfile(UserDTO userDTO, User user);

}
