package com.example.quanlysach.service;

import com.example.quanlysach.model.BookCategory;
import com.example.quanlysach.model.BookCategoryId;
import com.example.quanlysach.repository.BookCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookCategoryService {
    private final BookCategoryRepository bookCategoryRepo;

    public BookCategoryService(BookCategoryRepository bookCategoryRepo) {
        this.bookCategoryRepo = bookCategoryRepo;
    }

    public List<BookCategory> getAllBookCategories() {
        return bookCategoryRepo.findAll();
    }

    public BookCategory getBookCategoryById(BookCategoryId id) {
        return bookCategoryRepo.findById(id).orElseThrow();
    }

    public BookCategory add(BookCategory bookCategory) {
        return bookCategoryRepo.save(bookCategory);
    }

    public BookCategory delete(BookCategoryId id) {
        BookCategory bc = bookCategoryRepo.findById(id).orElseThrow();
        bc.setStatus("INACTIVE");
        return bookCategoryRepo.save(bc);
    }
}

