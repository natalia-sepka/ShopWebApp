package com.example.auth.services;

import com.example.auth.configuration.EmailConfiguration;
import com.example.auth.entity.User;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final EmailConfiguration emailConfiguration;
    @Value("${front.url}")
    private String frontendUrl;

    @Value("classpath:static/mail-activation.html")
    private Resource activeTemplate;

    @Value("classpath:static/reset-password.html")
    private Resource recoveryTemplate;

    public void sendActivation(User user) {
        try {
            String html = Files.toString(activeTemplate.getFile(), Charsets.UTF_8);
            html = html.replace("https://google.com", frontendUrl + "/activate/" + user.getUuid());
            emailConfiguration.sendMail(user.getEmail(), html, "Account activation", true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendPasswordRecovery(User user, String uid) {
        try {
            String html = Files.toString(recoveryTemplate.getFile(), Charsets.UTF_8);
            html = html.replace("https://google.com", frontendUrl + "/email-recovery" + uid);
            emailConfiguration.sendMail(user.getEmail(), html, "Email recovery", true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
