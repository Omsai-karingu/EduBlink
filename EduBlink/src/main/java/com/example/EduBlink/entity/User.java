package com.example.EduBlink.entity;

import jakarta.persistence.*;
//import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	@Column(unique = false, nullable = true)
	private String email;

	private String password;

	// OTP LOGIN FIELDS (nullable)
	@Column(nullable = false, unique = true)

	private String phone;

	@Column(nullable = true)
	private String otp;

	@Column(nullable = true)
	private Long otpExpiry;

	@Enumerated(EnumType.STRING)
//    @Builder.Default
	private Role role = Role.USER;

	public enum Role {
		ADMIN, USER
	}

}
