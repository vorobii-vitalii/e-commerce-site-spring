package com.commerce.web.model;

public final class UserVerificationFactory {

    public UserVerificationFactory() {
    }

    public static UserVerification create(
            User user,
            String token,
            Status status
    ) {
        UserVerification userVerification = new UserVerification();
        userVerification.setUser(user);
        userVerification.setToken(token);
        userVerification.setStatus(status);

        return userVerification;
    }

}
