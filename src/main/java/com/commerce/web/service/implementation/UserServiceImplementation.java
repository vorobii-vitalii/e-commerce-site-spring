package com.commerce.web.service.implementation;

import com.commerce.web.model.Role;
import com.commerce.web.model.Status;
import com.commerce.web.model.User;
import com.commerce.web.repository.RoleRepository;
import com.commerce.web.repository.UserRepository;
import com.commerce.web.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImplementation(UserRepository userRepository,RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
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
        user.setRoles(userRoles);

        // By default the new user is active
        user.setStatus( Status.ACTIVE );

        // Save user in database
        User registeredUser = userRepository.save(user);
        log.info("IN REGISTER use {} successfully registered", registeredUser);
        return registeredUser;
    }

    @Override
    public List<User> getAll () {
        List<User> fetchedUsers = userRepository.findAll ();
        log.info("IN getAll {} ", fetchedUsers);
        return fetchedUsers;
    }

    @Override
    public User findByUsername ( String username ) {
        User foundUser = userRepository.findByUsername ( username );
        //log.info ( "findByUsername {}", foundUser );
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
