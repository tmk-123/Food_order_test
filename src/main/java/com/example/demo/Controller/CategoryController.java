package com.example.demo.controller;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import com.example.demo.service.CategoryService;
import com.example.demo.model.Category;
import java.util.*;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    
    @GetMapping
    //  Model: Đối tượng chứa dữ liệu bạn muốn truyền từ Controller sang View (HTML).
    public String listCategory(Model model) {
        List<Category> categories = categoryService.findAll();

        // Gắn danh sách danh mục vào Model với tên categories. Trong file HTML, bạn có thể gọi ${categories} để lấy dữ liệu này.
        model.addAttribute("categories", categories);
        
        // Thêm một đối tượng Category rỗng vào model để dùng làm mẫu cho form thêm danh mục mới
        model.addAttribute("category", new Category());

        // Spring Boot sẽ tìm file src/main/resources/templates/category.html để render trang.
        return "category";
    }

    @PostMapping
    public String saveCategory(@ModelAttribute Category category) {
        categoryService.save(category);
        return "redirect:/categories";
    }

    @GetMapping("/edit/{id}")
    public String editCategory(@PathVariable Long id, Model model) {
        Category category = categoryService.getCategoryById(id);
        model.addAttribute("category", category);
        return "edit_category";
    } 

    @PostMapping("/update")
    public String updateCategories(@ModelAttribute Category category) {
        categoryService.save(category);
        return "redirect:/categories";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategories(@PathVariable Long id, Model model) {
        categoryService.deleteById(id);
        return "redirect:/categories";
    }
}
