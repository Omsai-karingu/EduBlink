package com.example.EduBlink.controller;

import com.example.EduBlink.entity.User;
import com.example.EduBlink.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin("*")
public class UserController {
	
	@Autowired
    UserService service;

    
    // GET USER BY EMAIL
    @GetMapping("/byEmail")
    public ResponseEntity<User> getUserByEmail(@RequestParam String email) {
        return ResponseEntity.ok(service.getUserByEmail(email));
    }

    // ADMIN: GET ALL USERS
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(service.getAllUsers());
    }

    // ADMIN: UPDATE ROLE
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin/role/{id}")
    public ResponseEntity<String> updateRole(
            @PathVariable Long id,
            @RequestParam String role) {
        service.updateRole(id, role);
        return ResponseEntity.ok("Role updated successfully");
    }
}
