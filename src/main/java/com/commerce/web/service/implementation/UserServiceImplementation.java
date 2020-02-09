package com.commerce.web.service.implementation;

import com.commerce.web.exceptions.UserIsAlreadyVerifiedException;
import com.commerce.web.exceptions.UserWasNotFoundByEmailException;
import com.commerce.web.exceptions.VerificationTokenExpiredException;
import com.commerce.web.exceptions.VerificationTokenHasNotMatchedException;
import com.commerce.web.model.*;
import com.commerce.web.repository.RoleRepository;
import com.commerce.web.repository.UserRepository;
import com.commerce.web.repository.UserVerificationRepository;
import com.commerce.web.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;
    private final UserVerificationRepository userVerificationRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImplementation(
                UserRepository userRepository,
                UserVerificationRepository userVerificationRepository,
                RoleRepository roleRepository,
                BCryptPasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.userVerificationRepository = userVerificationRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register ( User user ) {

        Role roleCustomer = roleRepository.findByName ( "ROLE_CUSTOMER" );

        List<Role> userRoles = new ArrayList<> ();

        // Attach customer role to new user
        userRoles.add ( roleCustomer );

        // Encode the user's password
        user.setPassword(passwordEncoder.encode ( user.getPassword() ));

        // Attach roles to user
        user.setRoles(userRoles);

        // By default the new user is not active (has to be verified)
        user.setStatus( Status.NOT_ACTIVE );

        User registeredUser = userRepository.save(user);

        // Generate random UUID
        UUID randomUUID = UUID.randomUUID ();

        UserVerification userVerification = userVerificationRepository.save( UserVerificationFactory.create (
                registeredUser, randomUUID.toString (), Status.ACTIVE
        ));

        registeredUser.setUserVerification ( userVerification );

        userRepository.save ( registeredUser );

        log.info("IN REGISTER use {} successfully registered", registeredUser);
        return registeredUser;
    }

    @Override
    public User verifyByToken ( String token ) throws VerificationTokenExpiredException, VerificationTokenHasNotMatchedException {

        UserVerification foundUserVerification = userVerificationRepository.findByToken ( token );

        if (foundUserVerification == null) {
            throw new VerificationTokenHasNotMatchedException ( "Token wasn't found" );
        }

        Long tokenExpirationTime = foundUserVerification.getCreated ().getTime ();
        Date validatedBy = new Date(tokenExpirationTime + 3600 * 2);

        if ( ! new Date ().before ( validatedBy)  ) {
            throw new VerificationTokenExpiredException ( "Token has expired" );
        }

        User matchedUser = foundUserVerification.getUser ();
        matchedUser.setStatus ( Status.ACTIVE );

        log.info ( "Verified user {} by token {}",matchedUser,token);

        userRepository.save ( matchedUser );

        return matchedUser;
    }

    @Override
    public String regenerateToken ( String email ) throws UserWasNotFoundByEmailException, UserIsAlreadyVerifiedException {

        User user = userRepository.findByEmail ( email );

        if (user == null) {
            throw new UserWasNotFoundByEmailException ( "User wasn't found by email: " + email );
        }

        if (user.getStatus () == Status.ACTIVE) {
            throw new UserIsAlreadyVerifiedException ( "User with email " + email + " is verified!" );
        }

        UserVerification userVerification = user.getUserVerification ();

        String newToken = UUID.randomUUID ().toString ();

        userVerification.setToken ( newToken );

        userVerificationRepository.save(userVerification);

        log.info ( "Generated new token for {} ", user );

        return newToken;
    }

    @Override
    public List<User> getAll () {
        List<User> fetchedUsers = userRepository.findAll ();
        log.info("IN getAll {} ", fetchedUsers);
        return fetchedUsers;
    }

    @Override
    public User findByEmail ( String email ) {
        User foundUser = userRepository.findByEmail ( email );
        log.info ( "findByEmail {}", foundUser );
        return foundUser;
    }

    @Override
    public User findById ( Long id ) {
        User foundUser = userRepository.findById ( id ).orElse ( null );
        log.info ( "findById {}", foundUser );
        return foundUser;
    }



    @Override
    public void delete ( Long id ) {
        userRepository.deleteById ( id );
        log.info ( "deleteById {}", id );
    }
}
