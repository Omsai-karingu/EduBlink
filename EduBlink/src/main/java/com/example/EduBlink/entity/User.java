package com.example.EduBlink.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    private String role;

    // OTP LOGIN FIELDS (nullable)
    @Column(nullable = true)
    private String phone;

    @Column(nullable = true)
    private String otp;

    @Column(nullable = true)
    private Long otpExpiryTime;
}
