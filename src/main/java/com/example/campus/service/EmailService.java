package com.example.campus.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import com.example.campus.entity.Event;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EmailService {

    // Existing fields and constructor

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    public void sendOtpEmail(String toEmail, String otp) {
        // Always print to console
        System.out.println("==================================================");
        System.out.println("OTP GENERATED FOR: " + toEmail);
        System.out.println("OTP CODE: " + otp);
        System.out.println("==================================================");

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("Your OTP for Campus Event Management Registration");
            message.setText("Welcome to Campus Event Management!\n\nYour OTP for registration is: " + otp + "\n\nPlease enter this code to verify your account.");
            
            mailSender.send(message);
            System.out.println("Email successfully sent to " + toEmail);
        } catch (Exception e) {
            System.err.println("WARNING: Failed to send real email. Ensure application.properties has valid SMTP credentials (like an App Password for Gmail).");
            e.printStackTrace();
        }
    }


    /**
     * Send a payment confirmation email to the student.
     */
    public void sendPaymentConfirmation(String toEmail, Event event) {
        String subject = "Payment Confirmation for " + event.getTitle();
        StringBuilder sb = new StringBuilder();
        sb.append("Dear Student,\n\n");
        sb.append("You have successfully completed payment for the event \"").append(event.getTitle()).append("\".\n\n");
        sb.append("Event Details:\n");
        sb.append("Date: ").append(event.getDate()).append("\n");
        sb.append("Time: ").append(event.getTime()).append("\n");
        sb.append("Location: ").append(event.getLocationAddress()).append("\n");
        sb.append("Amount: ");
        if (event.getPaymentAmount() != null) {
            sb.append("₹").append(event.getPaymentAmount());
        } else {
            sb.append("Free");
        }
        sb.append("\n\nThank you for joining!\n\nRegards,\nCampus Event Management Team");
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject(subject);
            message.setText(sb.toString());
            mailSender.send(message);
            System.out.println("Payment confirmation email sent to " + toEmail);
        } catch (Exception e) {
            System.err.println("Failed to send payment confirmation email to " + toEmail);
            e.printStackTrace();
        }
    }
}

