package com.example.quanlysach.repository;

import com.example.quanlysach.model.BookCategory;
import com.example.quanlysach.model.BookCategoryId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookCategoryRepository extends JpaRepository<BookCategory, BookCategoryId> {
}

