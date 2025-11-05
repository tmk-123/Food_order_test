package com.example.demo.service;

import com.example.demo.model.*;
import java.util.*;

public interface CartService {
    // thêm cart khi biết user, product
    void addToCart(User user, Product product);    

    // Lấy toàn bộ cart của 1 user
    List<Cart> getUserCart(User user);

    // Xóa 1 sản phẩm khỏi giỏ
    void removeFromCart(Long cartId);

    // Tính tổng số tiền trong giỏ đó
    double getTotalPrice(User user);
}
