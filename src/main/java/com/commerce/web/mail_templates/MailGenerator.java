package com.commerce.web.mail_templates;

import org.springframework.mail.SimpleMailMessage;

public interface MailGenerator {
    SimpleMailMessage generate();
}
