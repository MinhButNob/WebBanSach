package com.example.quanlysach.controller;

import com.example.quanlysach.dto.BookDTO;
import com.example.quanlysach.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@CrossOrigin("*")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // Lấy tất cả sách
    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        List<BookDTO> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    // Lấy sách theo ID
    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
        BookDTO book = bookService.getBookById(id);
        return ResponseEntity.ok(book);
    }

    // Tạo sách mới
    @PostMapping
    public ResponseEntity<BookDTO> createBook(@RequestBody BookDTO bookDTO) {
        BookDTO created = bookService.createBook(bookDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // Cập nhật sách
    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long id, @RequestBody BookDTO bookDTO) {
        BookDTO updated = bookService.updateBook(id, bookDTO);
        return ResponseEntity.ok(updated);
    }

    // Xóa sách (chuyển status thành INACTIVE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBookById(id);
        return ResponseEntity.noContent().build();
    }

    // Lấy sách theo thể loại
    @GetMapping("/category/{categoryName}")
    public ResponseEntity<List<BookDTO>> getBooksByCategory(@PathVariable String categoryName) {
        List<BookDTO> books = bookService.getBooksByCategory(categoryName);
        return ResponseEntity.ok(books);
    }

    // Lấy sách theo nhà xuất bản
    @GetMapping("/publisher/{publisherId}")
    public ResponseEntity<List<BookDTO>> getBooksByPublisher(@PathVariable Long publisherId) {
        List<BookDTO> books = bookService.getBooksByPublisher(publisherId);
        return ResponseEntity.ok(books);
    }

    // Tìm kiếm sách theo tên
    @GetMapping("/search")
    public ResponseEntity<List<BookDTO>> searchBooks(@RequestParam String keyword) {
        List<BookDTO> books = bookService.searchBooks(keyword);
        return ResponseEntity.ok(books);
    }

    // Lấy sách đang hoạt động
    @GetMapping("/active")
    public ResponseEntity<List<BookDTO>> getActiveBooks() {
        List<BookDTO> books = bookService.getActiveBooks();
        return ResponseEntity.ok(books);
    }
}

