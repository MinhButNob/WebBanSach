package com.example.quanlysach.service;

import com.example.quanlysach.model.Book;
import com.example.quanlysach.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepo;

    public BookService(BookRepository bookRepo) {
        this.bookRepo = bookRepo;
    }

    public List<Book> getAllBooks() {
        return bookRepo.findAll();
    }

    public Book getBookById(Long id) {
        return bookRepo.findById(id).orElseThrow();
    }

    public Book add(Book book) {
        return bookRepo.save(book);
    }

    public Book update(Long id, Book book) {
        Book b = bookRepo.findById(id).orElseThrow();
        b.setTitle(book.getTitle());
        b.setIsbn(book.getIsbn());
        b.setLanguage(book.getLanguage());
        b.setEdition(book.getEdition());
        b.setTotalCopies(book.getTotalCopies());
        b.setAvailableCopies(book.getAvailableCopies());
        b.setMinLoanDays(book.getMinLoanDays());
        b.setMaxLoanDays(book.getMaxLoanDays());
        b.setPopularityScore(book.getPopularityScore());
        b.setPublisherId(book.getPublisherId());
        b.setStatus(book.getStatus());
        return bookRepo.save(b);
    }

    public Book delete(Long id) {
        Book b = bookRepo.findById(id).orElseThrow();
        b.setStatus("INACTIVE");
        return bookRepo.save(b);
    }
}

