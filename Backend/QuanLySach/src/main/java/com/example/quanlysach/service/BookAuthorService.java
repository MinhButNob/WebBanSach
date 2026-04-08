package com.example.quanlysach.service;

import com.example.quanlysach.model.BookAuthor;
import com.example.quanlysach.model.BookAuthorId;
import com.example.quanlysach.repository.BookAuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookAuthorService {
    private final BookAuthorRepository bookAuthorRepo;

    public BookAuthorService(BookAuthorRepository bookAuthorRepo) {
        this.bookAuthorRepo = bookAuthorRepo;
    }

    public List<BookAuthor> getAllBookAuthors() {
        return bookAuthorRepo.findAll();
    }

    public BookAuthor getBookAuthorById(BookAuthorId id) {
        return bookAuthorRepo.findById(id).orElseThrow();
    }

    public BookAuthor add(BookAuthor bookAuthor) {
        return bookAuthorRepo.save(bookAuthor);
    }

    public BookAuthor delete(BookAuthorId id) {
        BookAuthor ba = bookAuthorRepo.findById(id).orElseThrow();
        ba.setStatus("INACTIVE");
        return bookAuthorRepo.save(ba);
    }
}

