package com.commerce.web.security;

import com.commerce.web.exceptions.UserWasNotFoundByEmailException;
import com.commerce.web.model.User;
import com.commerce.web.security.jwt.JwtUser;
import com.commerce.web.security.jwt.JwtUserFactory;
import com.commerce.web.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Qualifier("jwt_user_details_service")
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public JwtUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public JwtUser loadUserByUsername(String email) throws UsernameNotFoundException {

        try {

            User user = userService.findByEmail(email);

            JwtUser jwtUser = JwtUserFactory.create(user);
            log.info("loadByUserName - user with email {} successfully loaded", email);

            return jwtUser;
        } catch (UserWasNotFoundByEmailException e) {
            throw new UsernameNotFoundException("User with email " + email + " not found...");
        }

    }
}
