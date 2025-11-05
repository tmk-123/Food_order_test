package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UserService service;

    public AuthController(UserService service) {
        this.service = service;
    }

    // Hiển thị trang đăng ký
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    // Xử lý đăng ký
    @PostMapping("/register")
    public String processRegister(@ModelAttribute User user, Model model) {
        User registered = service.register(user);
        if (registered == null) {
            model.addAttribute("error", "Email đã được sử dụng!");
            return "register";
        }
        model.addAttribute("message", "Đăng ký thành công! Hãy đăng nhập.");
        return "login";
    }

    // Hiển thị trang login
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    // Xử lý login
    @PostMapping("/login")
    public String processLogin(@RequestParam String email,
                               @RequestParam String password,
                               HttpSession session,
                               Model model) {
        User user = service.login(email, password);
        if (user != null) {
            // định nghĩa thuộc tính loggerUser để biết đã đăng nhập chưa
            session.setAttribute("loggedUser", user);
            return "redirect:/home";
        }
        model.addAttribute("error", "Email hoặc mật khẩu sai!");
        return "login";
    }

    // Trang chủ (sau khi đăng nhập)
    @GetMapping("/home")
    public String home(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        return "index";
    }

    // Đăng xuất
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // xóa session
        return "redirect:/login?logout";
    }
}
