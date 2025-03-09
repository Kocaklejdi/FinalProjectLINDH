package com.example.JobSeekerPortal.service;

import com.example.JobSeekerPortal.dto.UserDTO;
import com.example.JobSeekerPortal.entity.JobListing;
import com.example.JobSeekerPortal.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Async
    public void sendAdminVerificationEmail(UserDTO userDTO, String token) {

        String email = "kocaklejdi@gmail.com";

        String username = userDTO.getUsername();

        String verificationLink = "http://localhost:8080/api/v1/auth/verify?token=" + token;
        System.out.println("Sending email...");
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("New Admin Verification Email");
            message.setText("The user "+ username + " wants to become an ADMIN. Verify with the link " + verificationLink);

            mailSender.send(message);
            System.out.println("Email sent successfully!");
        } catch (Exception e) {
            System.err.println("Error sending email: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Async
    public void sendVerificationEmail(String email,String token) {

        String verificationLink = "http://localhost:8080/api/v1/auth/verify?token=" + token;
        System.out.println("Sending email...");
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("User Verification Email");
            message.setText("Click the link to verify your accont -> " + verificationLink);

            mailSender.send(message);
            System.out.println("Email sent successfully!");
        } catch (Exception e) {
            System.err.println("Error sending email: " + e.getMessage());
            e.printStackTrace();
        }
    }
    @Async
    public void sendJobUpdateEmail(User user, JobListing jobListing, String status) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(user.getEmail());
            message.setSubject("Job application update");
            message.setText("Dear " + user.getUsername() +". Your job application for " + jobListing.getTitle() + " in " + jobListing.getLocation() + " has been " + status);

            mailSender.send(message);
            System.out.println("Email sent successfully!");
        } catch (Exception e) {
            System.err.println("Error sending email: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
