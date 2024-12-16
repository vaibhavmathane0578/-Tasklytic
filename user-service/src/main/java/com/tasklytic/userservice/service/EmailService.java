package com.tasklytic.userservice.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.tasklytic.userservice.constants.Constants;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;
    
    // Send OTP email
    public void sendOtpEmail(String email, String otp) {
        try {
            // Create a SimpleMailMessage object
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("noreply-verification@tasklytic.com"); // Sender's email
            message.setTo(email); // Recipient's email
            message.setSubject("Your OTP Code");
            message.setText("Dear User,\n\nYour OTP code is: " + otp + 
                            "\n\nPlease use this code to verify your identity. The code is valid for 5 minutes.\n\nThank you!");

            // Attempt to send the email
            mailSender.send(message);
            System.out.println("OTP email sent to " + email);
        } catch (Exception ex) {
            // Log the error and rethrow a custom exception
            System.err.println("Failed to send email to " + email + ": " + ex.getMessage());
            throw new Constants.EmailSendingException(
                String.format(Constants.EMAIL_SENDING_FAILED, email)
            );
        }
    }
}