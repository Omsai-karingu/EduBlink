package com.example.EduBlink.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.EduBlink.Repository.UserRepository;
import com.example.EduBlink.bean.JwtResponse;
import com.example.EduBlink.bean.LoginRequest;
import com.example.EduBlink.bean.RegisterRequest;
import com.example.EduBlink.entity.User;
import com.example.EduBlink.security.JwtService;
import com.example.EduBlink.service.AuthService;
import com.example.EduBlink.serviceimpl.StorageService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api")

public class AuthController {

   @Autowired
   AuthService service;
   
   @Autowired
   UserRepository userRepo;
   
   @Autowired
   StorageService storageService;
   
   @Autowired
   private JwtService jwtService;

   
   @PostMapping("/register")
   public ResponseEntity<JwtResponse> register(@RequestBody RegisterRequest request){
	  return service.register(request);
   }
   
   @PostMapping("/login")
   public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest request){
	   return service.login(request);
   }
   
   @PostMapping("/sendotp")
   public ResponseEntity<?> sendOtp(@RequestBody LoginRequest request) {
       return service.sendOtp(request);
   }

   @PostMapping("/verify-otp")
   public ResponseEntity<?> verifyOtp(@RequestBody LoginRequest req) {
       return service.verifyOtp(req);
   }
   
   @PostMapping("/uploads")
   public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file) {

       try {

           String fileName = storageService.saveFile(file);

           String imageUrl = "http://localhost:8080/uploads/" + fileName;

           return ResponseEntity.ok(Map.of("imageUrl", imageUrl));

       } catch (Exception e) {
           return ResponseEntity.badRequest()
                   .body(Map.of("error", e.getMessage()));
       }
   }
   
}
