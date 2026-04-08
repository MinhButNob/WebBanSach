package com.example.quanlysach.service;

import com.example.quanlysach.model.Category;
import com.example.quanlysach.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepo;

    public CategoryService(CategoryRepository categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    public List<Category> getAllCategories() {
        return categoryRepo.findAll();
    }

    public Category getCategoryById(Long id) {
        return categoryRepo.findById(id).orElseThrow();
    }

    public Category add(Category category) {
        return categoryRepo.save(category);
    }

    public Category update(Long id, Category category) {
        Category c = categoryRepo.findById(id).orElseThrow();
        c.setName(category.getName());
        c.setAverageLoanDays(category.getAverageLoanDays());
        c.setTotalBooks(category.getTotalBooks());
        return categoryRepo.save(c);
    }

    public Category delete(Long id) {
        Category c = categoryRepo.findById(id).orElseThrow();
        c.setStatus("INACTIVE");
        return categoryRepo.save(c);
    }
}

