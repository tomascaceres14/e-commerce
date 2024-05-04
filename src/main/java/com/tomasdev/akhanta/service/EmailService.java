package com.tomasdev.akhanta.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;

import java.io.IOException;

public interface EmailService {

    void sendEmail(String to, String subject) throws MessagingException, IOException;
}
