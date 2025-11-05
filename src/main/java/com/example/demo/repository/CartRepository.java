package com.example.demo.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.Cart;
import java.util.List;
import com.example.demo.model.User;
import com.example.demo.model.Product;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByUser(User user);

    // Khi muốn tăng số lượng đơn hàng lên mà không muốn nhập lại
    Cart findByUserAndProduct(User user, Product product);
}
