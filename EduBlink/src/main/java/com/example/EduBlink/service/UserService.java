package com.example.EduBlink.service;

import com.example.EduBlink.entity.User;
import java.util.List;

public interface UserService {

    User register(User user);
    String login(String email, String password);
    User getUserByEmail(String email);
    List<User> getAllUsers();
    void updateRole(Long userId, String role);
}
