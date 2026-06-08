//package com.example.EduBlink.serviceimpl;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.mail.SimpleMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//@Service
//public class EmailService {
//
//    @Autowired
//    private JavaMailSender mailSender;
//
//    public void sendOtp(String toEmail, String otp) {
//
//        SimpleMailMessage message = new SimpleMailMessage();
//
//        message.setTo(toEmail);
//        message.setSubject("EduBlink - OTP Verification");
//        message.setText("Your OTP is: " + otp + "\nValid for 5 minutes.");
//
//        mailSender.send(message);
//    }
//}
