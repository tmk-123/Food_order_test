package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.demo.service.CartService;
import com.example.demo.service.ProductService;
import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.model.Cart;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import java.util.*;

@Controller
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;
    private final ProductService productService;

    public CartController(CartService cartService, ProductService productService) {
        this.cartService = cartService;
        this.productService = productService;
    }

    @GetMapping("/add/{id}")
    // Lấy đối tượng Session hiện tại của người dùng. Session dùng để lưu trữ trạng thái người dùng (như thông tin đăng nhập) giữa các yêu cầu.
    public String addToCart(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null) return "redirect:/login";
        
        // Gọi đến một Service Layer (productService) để truy vấn và lấy đối tượng Product hoàn chỉnh từ cơ sở dữ liệu dựa trên id nhận được từ URL.
        Product product = productService.getProductById(id);

        cartService.addToCart(user, product);
        return "redirect:/cart";
    }

    // Hiển thị giỏ hàng
    @GetMapping
    public String showCart(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null) return "redirect:/login";

        List<Cart> cartItems = cartService.getUserCart(user);
        double total = cartService.getTotalPrice(user);

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("total", total);
        
        // sang cart.html
        return "cart";
    }

    // Xóa sản phẩm khỏi giỏ
    @GetMapping("/remove/{id}")
    public String removeFromCart(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null) return "redirect:/login";
        
        cartService.removeFromCart(id);
        return "redirect:/cart";
    }
}
