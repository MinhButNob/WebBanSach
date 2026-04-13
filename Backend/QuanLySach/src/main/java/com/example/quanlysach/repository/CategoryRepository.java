package com.example.quanlysach.repository;

import com.example.quanlysach.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    // Tìm thể loại theo tên
    Optional<Category> findByName(String categoryName);

    // Các method khác nếu cần
    boolean existsByName(String name);
}

