package com.commerce.web.model;

public final class UserPasswordResetFactory {

    public UserPasswordResetFactory() {

    }

    public static UserPasswordReset create(String token,User user,Status status) {

        UserPasswordReset userPasswordReset = new UserPasswordReset ();

        userPasswordReset.setToken ( token );
        userPasswordReset.setUser ( user );
        userPasswordReset.setStatus ( status );

        return userPasswordReset;
    }

}
