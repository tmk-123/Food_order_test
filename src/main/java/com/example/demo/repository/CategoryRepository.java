package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.Category;

// Spring Boot tự động thêm “@Repository” ngầm cho bạn vì đã gọi thẳng JpaRepository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}