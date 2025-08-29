package com.ofss.bankapp.services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendOtp(String toEmail, String otpCode, String purpose) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(toEmail);
        msg.setSubject("Your OTP for " + purpose);
        msg.setText("Dear Customer,\n\nYour OTP is: " + otpCode + 
                   "\nThis OTP will expire in 5 minutes.\n\n- BankApp Team");

        mailSender.send(msg);
    }
}
