package com.commerce.web.mail_templates;

import org.springframework.mail.SimpleMailMessage;

public class ForgotPasswordMailMessage implements MailGenerator {

    private String email;
    private String token;
    private String baseUrl;

    public ForgotPasswordMailMessage(String email,String token,String baseUrl) {
        this.email = email;
        this.token = token;
        this.baseUrl = baseUrl;
    }

    @Override
    public SimpleMailMessage generate () {
        SimpleMailMessage mailMessage = new SimpleMailMessage ( );
        mailMessage.setTo ( email );
        mailMessage.setSubject ( "Reset password" );
        mailMessage.setText ( "You can reset your password by link: " + baseUrl + token + " .");
        return mailMessage;
    }
}
