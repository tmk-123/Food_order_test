package com.example.demo.service.impl;

import org.springframework.stereotype.Service;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repo;

    public UserServiceImpl(UserRepository repo) {
        this.repo = repo;
    }

    // Đăng ký tài khoản
    @Override
    public User register(User user) {
        if (repo.findByEmail(user.getEmail()) != null) {
            return null; // Email đã tồn tại
        }
        return repo.save(user);
    }

    // Đăng nhập
    @Override
    public User login(String email, String password) {
        User user = repo.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return user; // đăng nhập đúng
        }
        return null; // sai email hoặc password
    }
}
