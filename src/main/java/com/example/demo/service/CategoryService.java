package com.example.demo.service;

import com.example.demo.model.Category;
import java.util.List;

public interface CategoryService {    
    List<Category> findAll();
    Category save(Category category);
    Category getCategoryById(Long id);
    void deleteById(Long id); 
}   
