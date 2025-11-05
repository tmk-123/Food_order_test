package com.example.demo.service.impl;

import org.springframework.stereotype.Service;
import com.example.demo.repository.CartRepository;
import com.example.demo.service.CartService;
import com.example.demo.model.Cart;
import com.example.demo.model.Product;
import com.example.demo.model.User;
import java.util.*;

@Service
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;

    public CartServiceImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public void addToCart(User user, Product product) {
        Cart existing = cartRepository.findByUserAndProduct(user, product);
        
        // Nếu có rồi thì tăng số lượng, khoogn thì tạo mới 
        if (existing != null) {
            existing.setQuantity(existing.getQuantity() + 1);
            cartRepository.save(existing);
        }
        else {
            Cart cart = new Cart();
            cart.setUser(user);
            cart.setProduct(product);
            cart.setQuantity(1);
            cartRepository.save(cart);
        }
    }

    @Override
    public List<Cart> getUserCart(User user) {
        return cartRepository.findByUser(user);
    }

    @Override
    public void removeFromCart(Long cartId) {
        cartRepository.deleteById(cartId);
    }

    @Override
    public double getTotalPrice(User user) {
        List<Cart> carts = cartRepository.findByUser(user);
        double total = 0;
        for (Cart c : carts) {
            double price = c.getProduct().getPrice();
            int quantity = c.getQuantity();
            total += price*quantity;
        }
        return total;
    } 
}
