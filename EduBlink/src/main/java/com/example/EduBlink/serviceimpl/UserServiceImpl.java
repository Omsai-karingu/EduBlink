package com.example.EduBlink.serviceimpl;


import com.example.EduBlink.entity.User;
import com.example.EduBlink.security.JwtService;
import com.example.EduBlink.Repository.UserRepository;
import com.example.EduBlink.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;
    private final JwtService jwtUtil;

    public UserServiceImpl(UserRepository repo,
                           PasswordEncoder encoder,
                           JwtService jwtUtil) {
        this.repo = repo;
        this.encoder = encoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public User register(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole("ROLE_STUDENT");
        return repo.save(user);
    }

    @Override
    public String login(String email, String password) {
        User user = repo.findByEmail(email).orElseThrow();
        if (!encoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        return jwtUtil.generateToken(email);
    }

    @Override
    public User getUserByEmail(String email) {
        return repo.findByEmail(email).orElseThrow();
    }

    @Override
    public List<User> getAllUsers() {
        return repo.findAll();
    }

    @Override
    public void updateRole(Long userId, String role) {
        User user = repo.findById(userId).orElseThrow();
        user.setRole(role);
        repo.save(user);
    }
}
