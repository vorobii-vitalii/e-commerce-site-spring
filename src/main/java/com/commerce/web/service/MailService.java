package com.commerce.web.service;

import org.springframework.mail.SimpleMailMessage;

public interface MailService {
    void send(SimpleMailMessage simpleMailMessage);
}
