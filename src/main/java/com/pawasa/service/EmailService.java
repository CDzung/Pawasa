package com.pawasa.service;

import javax.mail.MessagingException;

public interface EmailService {
    public abstract void sendEmail(String to, String subject, String body) throws MessagingException;
}
