package com.example.EduBlink.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.EduBlink.Repository.UserRepository;
import com.example.EduBlink.bean.JwtResponse;
import com.example.EduBlink.bean.LoginRequest;
import com.example.EduBlink.bean.RegisterRequest;
import com.example.EduBlink.entity.User;
import com.example.EduBlink.service.AuthService;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

   @Autowired
   AuthService service;
   
   @Autowired
   UserRepository userRepo;
   
   @PostMapping("/register")
   public ResponseEntity<JwtResponse> register(@RequestBody RegisterRequest request){
	  return service.register(request);
   }
   
   @PostMapping("/login")
   public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest request){
	   return service.login(request);
   }
   
   @PostMapping("/sendotp")
   public ResponseEntity<JwtResponse> sendotp(@RequestBody LoginRequest request){
	   return service.sendotp(request);
   }
   
}
