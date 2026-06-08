package com.example.EduBlink.serviceimpl;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.List;
import java.util.Map;

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
    UserRepository userRepo;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;
    
    private final SecureRandom random = new SecureRandom();

    // ================= REGISTER =================
    @Override
    public ResponseEntity<JwtResponse> register(RegisterRequest request) {

        if (userRepo.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest()
                    .body(new JwtResponse("failed", 400, null, "Email already exists",null,null));
        }

        if (userRepo.findByPhone(request.getPhone()).isPresent()) {
            return ResponseEntity.badRequest()
                    .body(new JwtResponse("failed", 400, null, "Phone already exists", null,null));
        }

        String phone = request.getPhone();

        if (!phone.startsWith("+91")) {
            phone = "+91" + phone;
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(phone);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(User.Role.USER);

        userRepo.save(user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new JwtResponse("success", 201, null, "Registered successfully", null,null));
    }

    // ================= LOGIN =================
    @Override
    public ResponseEntity<JwtResponse> login(LoginRequest request) {

        User user = userRepo.findByEmail(request.getEmail()).orElse(null);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new JwtResponse(
                            "failed",
                            404,
                            null,
                            "User not found",
                            null,
                            null
                    ));
        }

        // ===== PASSWORD LOGIN =====
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {

            try {

                Authentication authentication =
                        authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(
                                        request.getEmail(),
                                        request.getPassword()
                                )
                        );

                if (authentication.isAuthenticated()) {

                    String token =
                            jwtService.generateToken(user.getEmail(),user.getRole().name());

                    // ✅ Convert role to List
                    List<String> roles =
                            List.of(user.getRole().name());

                    return ResponseEntity.ok(
                            new JwtResponse(
                                    "success",
                                    200,
                                    token,
                                    "Login successful",
                                    roles,
                                    user
                                   
                            )
                    );
                }

            } catch (Exception e) {

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new JwtResponse(
                                "failed",
                                401,
                                null,
                                "Invalid credentials",
                                null,
                                null
                        ));
            }
        }

        // ===== OTP LOGIN =====
        if (request.getOtp() != null && !request.getOtp().isEmpty()) {

            if (user.getOtp() == null ||
                    user.getOtpExpiry() == null ||
                    Instant.now().toEpochMilli() > user.getOtpExpiry()) {

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new JwtResponse(
                                "failed",
                                401,
                                null,
                                "OTP expired or invalid",
                                null,
                                null
                        ));
            }

            if (!request.getOtp().equals(user.getOtp())) {

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new JwtResponse(
                                "failed",
                                401,
                                null,
                                "Incorrect OTP",
                                null,
                                null
                        ));
            }
            
            String role = user.getRole().name();
            
            String token =
                    jwtService.generateToken(user.getEmail(),role);

            user.setOtp(null);
            user.setOtpExpiry(null);
            userRepo.save(user);

            List<String> roles =
                    List.of(role);

            return ResponseEntity.ok(
                    new JwtResponse(
                            "success",
                            200,
                            token,
                            "OTP login successful",
                            roles,
                            user
                    )
            );
        }

        return ResponseEntity.badRequest()
                .body(new JwtResponse(
                        "failed",
                        400,
                        null,
                        "Provide password or OTP",
                        null,
                        null
                ));
    }
    // ================= SEND OTP =================
    @Override
    public ResponseEntity<?> sendOtp(LoginRequest request) {

        String phone = request.getPhone();

        if (phone == null || phone.isEmpty()) {
            return ResponseEntity.badRequest().body("Phone required");
        }

        if (!phone.startsWith("+91")) {
            phone = "+91" + phone;
        }

        User user = userRepo.findByPhone(phone).orElse(null);

        if (user == null) {
            return ResponseEntity.badRequest()
                    .body("User not registered");
        }

        // Generate OTP
        String otp = String.format("%06d",
                random.nextInt(1000000));

        // Save
        user.setOtp(otp);
        user.setOtpExpiry(
                System.currentTimeMillis() + 5 * 60 * 1000
        );

        userRepo.save(user);

        // SIMULATION (Show OTP in Console)
        System.out.println("================================");
        System.out.println("📱 OTP for " + phone + " = " + otp);
        System.out.println("================================");

        return ResponseEntity.ok(
                "OTP Sent (Check Console)"
        );
    }

    // ================= VERIFY OTP =================
    @Override
    public ResponseEntity<?> verifyOtp(LoginRequest request) {

        String phone = request.getPhone();
        String otp = request.getOtp();

        if (phone == null || otp == null) {
            return ResponseEntity.badRequest()
                    .body("Phone & OTP required");
        }

        if (!phone.startsWith("+91")) {
            phone = "+91" + phone;
        }

        User user = userRepo.findByPhone(phone).orElse(null);

        if (user == null) {
            return ResponseEntity.badRequest()
                    .body("User not found");
        }

        if (user.getOtp() == null) {
            return ResponseEntity.badRequest()
                    .body("Send OTP first");
        }

        // Expiry
        if (System.currentTimeMillis() > user.getOtpExpiry()) {
            return ResponseEntity.badRequest()
                    .body("OTP Expired");
        }

        // Match
        if (!otp.equals(user.getOtp())) {
            return ResponseEntity.badRequest()
                    .body("Invalid OTP");
        }

        // Clear
        user.setOtp(null);
        user.setOtpExpiry(null);

        userRepo.save(user);
        
        String token = jwtService.generateToken(user.getEmail(),user.getRole().name());
        
        
        return ResponseEntity.ok(Map.of(
        		"token",token,
        		"user",user,
        		"role",user.getRole(),
        		"message", "Login Successful"
        		));
    }
}
