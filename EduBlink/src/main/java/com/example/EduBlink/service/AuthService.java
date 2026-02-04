package com.example.EduBlink.service;

import org.springframework.http.ResponseEntity;

import com.example.EduBlink.bean.JwtResponse;
import com.example.EduBlink.bean.LoginRequest;
import com.example.EduBlink.bean.RegisterRequest;

public interface AuthService {

	   public ResponseEntity<JwtResponse> register(RegisterRequest request);
	   
	   public ResponseEntity<JwtResponse> login(LoginRequest request);
	   
	   public ResponseEntity<JwtResponse> sendotp(LoginRequest request);
	
}
