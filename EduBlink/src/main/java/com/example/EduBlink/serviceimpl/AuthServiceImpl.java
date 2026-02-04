package com.example.EduBlink.serviceimpl;

import java.time.Instant;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.EduBlink.Repository.UserRepository;
import com.example.EduBlink.bean.JwtResponse;
import com.example.EduBlink.bean.LoginRequest;
import com.example.EduBlink.bean.RegisterRequest;
import com.example.EduBlink.entity.User;
import com.example.EduBlink.security.JwtService;
import com.example.EduBlink.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private OtpService otpService;

    // ================= REGISTER =================
    @Override
    public ResponseEntity<JwtResponse> register(RegisterRequest request) {

        if (userRepo.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new JwtResponse("failed", 400, null, "Email already exists"));
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("USER");

        userRepo.save(user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new JwtResponse("success", 201, null, "User registered successfully"));
    }

    // ================= LOGIN =================
    @Override
    public ResponseEntity<JwtResponse> login(LoginRequest request) {

        User user = userRepo.findByEmail(request.getEmail()).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new JwtResponse("failed", 404, null, "User not found"));
        }

        // 🔐 PASSWORD LOGIN
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            try {
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getEmail(),
                                request.getPassword()
                        )
                );

                if (authentication.isAuthenticated()) {
                    String token = jwtService.generateToken(user.getEmail());
                    return ResponseEntity.ok(
                            new JwtResponse("success", 200, token, "Login successful")
                    );
                }
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new JwtResponse("failed", 401, null, "Invalid credentials"));
            }
        }

        // 🔑 OTP LOGIN
        if (request.getOtp() != null && !request.getOtp().isEmpty()) {

            if (user.getOtp() == null ||
                user.getOtpExpiryTime() == null ||
                Instant.now().toEpochMilli() > user.getOtpExpiryTime()) {

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new JwtResponse("failed", 401, null, "OTP expired or invalid"));
            }

            if (!request.getOtp().equals(user.getOtp())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new JwtResponse("failed", 401, null, "Incorrect OTP"));
            }

            String token = jwtService.generateToken(user.getEmail());

            // Clear OTP after success
            user.setOtp(null);
            user.setOtpExpiryTime(null);
            userRepo.save(user);

            return ResponseEntity.ok(
                    new JwtResponse("success", 200, token, "OTP login successful")
            );
        }

        return ResponseEntity.badRequest()
                .body(new JwtResponse("failed", 400, null, "Provide password or OTP"));
    }

    // ================= SEND OTP =================
    @Override
    public ResponseEntity<JwtResponse> sendotp(LoginRequest request) {

        if (request.getEmail() == null || request.getEmail().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(new JwtResponse("failed", 400, null, "Email required"));
        }

        User user = userRepo.findByEmail(request.getEmail()).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new JwtResponse("failed", 404, null, "User not found"));
        }

        // Save phone only once
        if (user.getPhone() == null || user.getPhone().isEmpty()) {
            if (request.getPhone() == null || request.getPhone().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(new JwtResponse("failed", 400, null,
                                "Phone number required for OTP"));
            }
            user.setPhone(request.getPhone());
        }

        String phoneNumber = user.getPhone();
        if (!phoneNumber.startsWith("+")) {
            phoneNumber = "+91" + phoneNumber;
        }

        String otp = String.valueOf(100000 + new Random().nextInt(900000));

        otpService.sendOtp(phoneNumber, otp);

        user.setOtp(otp);
        user.setOtpExpiryTime(Instant.now().plusSeconds(300).toEpochMilli());
        userRepo.save(user);

        return ResponseEntity.ok(
                new JwtResponse("success", 200, null, "OTP sent successfully")
        );
    }
}
