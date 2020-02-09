package com.commerce.web.mail_templates;


import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;

public class AccountValidationMailMessage implements MailGenerator {

    private String email;
    private String token;
    private String baseUrl;

    public AccountValidationMailMessage(String email,String token,String baseUrl) {
        this.email = email;
        this.token = token;
        this.baseUrl = baseUrl;
    }

    @Override
    public SimpleMailMessage generate () {

        SimpleMailMessage accountValidationMail = new SimpleMailMessage ( );
        accountValidationMail.setTo ( email );
        accountValidationMail.setSubject ( "Email verification" );
        accountValidationMail.setText ( "Validate your account by link " + baseUrl + token );

        return accountValidationMail;
    }


}
