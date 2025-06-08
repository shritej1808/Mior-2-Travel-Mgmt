package com.example.Travel_mgmt_minor.service;


import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendBookingConfirmation(String to, String username, String packageName) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Booking Confirmation");
        message.setText("Dear " + username + ",\n\nYour booking for \"" + packageName + "\" is confirmed.\n\nThank you for choosing us!");
        message.setFrom("your-email@gmail.com");
        mailSender.send(message);
    }
}

