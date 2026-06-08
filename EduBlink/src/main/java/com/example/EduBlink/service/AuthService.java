package com.example.EduBlink.service;

import org.springframework.http.ResponseEntity;
import com.example.EduBlink.bean.JwtResponse;
import com.example.EduBlink.bean.LoginRequest;
import com.example.EduBlink.bean.RegisterRequest;

public interface AuthService {

    ResponseEntity<JwtResponse> register(RegisterRequest request);

    ResponseEntity<JwtResponse> login(LoginRequest request);

    ResponseEntity<?> sendOtp(LoginRequest request);

    ResponseEntity<?> verifyOtp(LoginRequest request);
}
