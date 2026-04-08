package com.example.quanlysach.service;

import com.example.quanlysach.model.BookCopy;
import com.example.quanlysach.repository.BookCopyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookCopyService {
    private final BookCopyRepository bookCopyRepo;

    public BookCopyService(BookCopyRepository bookCopyRepo) {
        this.bookCopyRepo = bookCopyRepo;
    }

    public List<BookCopy> getAllBookCopies() {
        return bookCopyRepo.findAll();
    }

    public BookCopy getBookCopyById(Long id) {
        return bookCopyRepo.findById(id).orElseThrow();
    }

    public BookCopy add(BookCopy bookCopy) {
        return bookCopyRepo.save(bookCopy);
    }

    public BookCopy update(Long id, BookCopy bookCopy) {
        BookCopy bc = bookCopyRepo.findById(id).orElseThrow();
        bc.setBookId(bookCopy.getBookId());
        bc.setBarcode(bookCopy.getBarcode());
        bc.setStatus(bookCopy.getStatus());
        bc.setShelfId(bookCopy.getShelfId());
        return bookCopyRepo.save(bc);
    }

    public BookCopy delete(Long id) {
        BookCopy bc = bookCopyRepo.findById(id).orElseThrow();
        bc.setStatus("INACTIVE");
        return bookCopyRepo.save(bc);
    }
}

