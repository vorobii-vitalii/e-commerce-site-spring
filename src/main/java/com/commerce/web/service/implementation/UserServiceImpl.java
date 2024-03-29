package com.commerce.web.service.implementation;

import com.commerce.web.dto.UserDTO;
import com.commerce.web.exceptions.*;
import com.commerce.web.model.*;
import com.commerce.web.repository.RoleRepository;
import com.commerce.web.repository.UserPasswordResetRepository;
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
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserVerificationRepository userVerificationRepository;
    private final UserPasswordResetRepository userPasswordResetRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl (
                UserRepository userRepository,
                UserVerificationRepository userVerificationRepository,
                UserPasswordResetRepository userPasswordResetRepository,
                RoleRepository roleRepository,
                BCryptPasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.userVerificationRepository = userVerificationRepository;
        this.userPasswordResetRepository = userPasswordResetRepository;
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

        long tokenExpirationTime = foundUserVerification.getCreated ().getTime ();
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
    public String regenerateVerificationToken ( String email ) throws UserWasNotFoundByEmailException, UserIsAlreadyVerifiedException {

        User user = userRepository.findByEmail ( email );

        if (user == null) {
            throw new UserWasNotFoundByEmailException ( "User wasn't found by email: " + email );
        }

        if (user.getStatus () == Status.ACTIVE) {
            throw new UserIsAlreadyVerifiedException ( "User with email " + email + " is verified!" );
        }

        UserVerification userVerification = user.getUserVerification ();

        String newToken = UUID.randomUUID ().toString ();

        userVerification.setCreated ( new Date() );
        userVerification.setToken ( newToken );

        userVerificationRepository.save(userVerification);

        log.info ( "Generated new token for {} ", user );

        return newToken;
    }

    @Override
    public String resetPasswordByEmail ( String email ) throws UserWasNotFoundByEmailException, UserNotActiveException {

        User user = userRepository.findByEmail ( email );

        if ( user == null )
            throw new UserWasNotFoundByEmailException ( "User wasn't found by email: " + email );

        if ( user.getStatus () != Status.ACTIVE )
            throw new UserNotActiveException ( "User is not yet active to change his password" );

        String token = UUID.randomUUID ().toString ();

        UserPasswordReset userPasswordReset = userPasswordResetRepository.save ( UserPasswordResetFactory.create ( token, user, Status.ACTIVE ));

        user.setUserPasswordReset ( userPasswordReset );

        log.info ( "Trying to reset password by email {} ", email );

        userRepository.save ( user );

        return token;
    }

    @Override
    public void changePasswordByToken ( String token, String password ) throws PasswordResetTokenIsNotValid,  PasswordResetTokenIsNotActive, PasswordResetTokenHasExpired {

        UserPasswordReset userPasswordReset = userPasswordResetRepository.findByToken ( token );

        if (userPasswordReset == null)
                throw new PasswordResetTokenIsNotValid ( "Password reset token " + token + " is invalid" );

        if (userPasswordReset.getStatus () != Status.ACTIVE)
                throw new PasswordResetTokenIsNotActive ( "Password reset token " + token + " is not active" );

        long tokenExpirationTime = userPasswordReset.getCreated ().getTime ();
        Date validatedBy = new Date(tokenExpirationTime + 3600 * 2);

        if ( ! new Date ().before ( validatedBy)  )
            throw new PasswordResetTokenHasExpired ( "Password reset token " + token + " has expired" );

        User userToChange = userPasswordReset.getUser ();

        userToChange.setPassword ( passwordEncoder.encode ( password ) );

        userPasswordReset.setStatus ( Status.NOT_ACTIVE );
        userPasswordResetRepository.save ( userPasswordReset );

        log.info ( "User {} has changed his password.", userToChange );

        userRepository.save ( userToChange );
    }


    @Override
    public List<User> getAll () throws UsersResultIsEmptyException {
        List<User> fetchedUsers = userRepository.findAll ();

        if (fetchedUsers.isEmpty ())
            throw new UsersResultIsEmptyException ( "Users were not found" );

        log.info("IN getAll {} ", fetchedUsers);
        return fetchedUsers;
    }

    @Override
    public User findByEmail ( String email ) throws UserWasNotFoundByEmailException {
        User foundUser = userRepository.findByEmail ( email );
        if (foundUser == null)
            throw new UserWasNotFoundByEmailException ( "User with email " + email + " not found" );
        log.info ( "findByEmail {}", foundUser );
        return foundUser;
    }

    @Override
    public User findById ( Long id ) throws UserWasNotFoundException, UserIsDeletedException {
        User foundUser = userRepository.findById ( id ).orElse ( null );

        if (foundUser == null)
            throw new UserWasNotFoundException ( "User with id " + id + " not found" );

        if (foundUser.getStatus () == Status.DELETED ) {
            throw new UserIsDeletedException ( "User with id " + id + " is deleted" );
        }

        log.info ( "findById {}", foundUser );
        return foundUser;
    }


    @Override
    public void changeUser ( Long id , UserDTO userDTO ) throws UserWasNotFoundException, RolesAreInvalidException, AdminIsImmutableException {

        User foundUser = userRepository.findById ( id ).orElse ( null );

        if (foundUser == null)
            throw new UserWasNotFoundException ( "User with id " + id + " not found" );

        if (foundUser.getRoles ().stream ().anyMatch ( role -> role.getName ().equals (  "ROLE_ADMIN" ) )) {
            throw new AdminIsImmutableException ( "Admin is immutable" );
        }

        List<Role> providedRoles = userDTO.getRoles ();
        Status providedStatus = userDTO.getStatus ();

        if (providedRoles != null) {

            List<Role> rolesToAttach = providedRoles
                    .stream ()
                    .map ( role -> roleRepository.findByName ( role.getName ()) )
                    .filter ( role -> role != null )
                    .collect( Collectors.toList());

            if (rolesToAttach.size () != providedRoles.size ()) {
                throw new RolesAreInvalidException ( "Roles are invalid" );
            }

            foundUser.setRoles ( rolesToAttach );

        }

        if (providedStatus != null) {
            foundUser.setStatus ( providedStatus );
        }

        log.info ( "Updated user {}", foundUser );

        log.info ( "USER ROLES {}", foundUser.getRoles () );

        userRepository.save ( foundUser );
    }


    @Override
    public void deleteUser ( Long id ) throws UserWasNotFoundException, AdminIsImmutableException {

        User foundUser = userRepository.findById ( id ).orElse ( null );

        if (foundUser == null)
            throw new UserWasNotFoundException ( "User with id " + id + " not found" );

        if (foundUser.getRoles ().stream ().anyMatch ( role -> role.getName ().equals (  "ROLE_ADMIN" ) )) {
            throw new AdminIsImmutableException ( "Admin is immutable" );
        }

        foundUser.setStatus ( Status.DELETED );

        userRepository.save ( foundUser );

        log.info ( "deleteById {}", id );
    }

}
