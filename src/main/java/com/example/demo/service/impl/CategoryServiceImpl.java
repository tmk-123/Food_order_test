package com.example.demo.service.impl;

import org.springframework.stereotype.Service;
import com.example.demo.service.CategoryService;
import com.example.demo.model.Category;
import com.example.demo.repository.CategoryRepository;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository repo;

    public CategoryServiceImpl(CategoryRepository repo) {
        this.repo = repo;
    }
    
    @Override
    public List<Category> findAll() {
        return repo.findAll();
    }

    @Override
    public Category save(Category category) {
        return repo.save(category);
    }

    @Override
    public Category getCategoryById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        repo.deleteById(id);
    }
}
