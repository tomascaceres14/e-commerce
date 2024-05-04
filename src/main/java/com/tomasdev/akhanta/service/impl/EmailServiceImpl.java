package com.tomasdev.akhanta.service.impl;

import com.tomasdev.akhanta.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.FileCopyUtils;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final ResourceLoader resourceLoader;

    @Override
    public void sendEmail(String to, String subject) throws MessagingException, IOException {
        MimeMessage message = mailSender.createMimeMessage();
        message.setFrom(new InternetAddress("tomialegriacaceres@gmail.com"));
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress("tomialegriacaceres@gmail.com"));
        message.setSubject(subject);

        String template = loadTemplateAsString("classpath:templates/template.html");

        template = template.replace("${username}", "Isabela");

        message.setContent(template, "text/html; charset=utf-8");


        mailSender.send(message);
    }

    private String loadTemplateAsString(String templatePath) throws IOException {
        Resource resource = resourceLoader.getResource(templatePath);
        try (InputStreamReader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)) {
            return FileCopyUtils.copyToString(reader);
        }
    }
}
