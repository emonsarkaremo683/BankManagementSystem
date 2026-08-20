package com.elitetech_inc.ensarkbank.common.email;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@Slf4j
public class MailConfig {

    @Bean
    @ConditionalOnProperty(name = "spring.mail.host", havingValue = "localhost")
    public JavaMailSender devMailSender() {
        log.warn("SMTP host is set to localhost - emails will be sent to local port 25");
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("localhost");
        mailSender.setPort(25);
        return mailSender;
    }
}

