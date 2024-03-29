package com.commerce.web.exceptions;


import org.springframework.security.core.AuthenticationException;

public class JwtAuthenthicationException extends AuthenticationException {

    public JwtAuthenthicationException ( String msg , Throwable t ) {
        super ( msg , t );
    }

    public JwtAuthenthicationException ( String msg ) {
        super ( msg );
    }
}
