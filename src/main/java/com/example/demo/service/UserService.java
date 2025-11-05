package com.example.demo.service;

import com.example.demo.model.User;

public interface UserService {
    User register(User user);
    User login(String email, String password);
}
