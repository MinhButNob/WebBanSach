package com.example.quanlysach.controller;

import com.example.quanlysach.dto.BookCopyDTO;
import com.example.quanlysach.dto.BookCopyRequestDTO;
import com.example.quanlysach.service.BookCopyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book-copies")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class BookCopyController {
    private final BookCopyService service;

    public BookCopyController(BookCopyService service) {
        this.service = service;
    }

    @GetMapping("/with-details")
    public ResponseEntity<List<BookCopyDTO>> getAllBookCopiesWithDetails() {
        List<BookCopyDTO> bookCopies = service.getAllBookCopies();
        return ResponseEntity.ok(bookCopies);
    }

    // Lấy tất cả bản sao sách
    @GetMapping
    public ResponseEntity<List<BookCopyDTO>> getAllBookCopies() {
        List<BookCopyDTO> bookCopies = service.getAllBookCopies();
        return ResponseEntity.ok(bookCopies);
    }

    // Lấy bản sao sách theo ID
    @GetMapping("/{id}")
    public ResponseEntity<BookCopyDTO> getBookCopyById(@PathVariable Long id) {
        BookCopyDTO bookCopy = service.getBookCopyById(id);
        return ResponseEntity.ok(bookCopy);
    }

    // Thêm mới bản sao sách
    @PostMapping
    public ResponseEntity<BookCopyDTO> addBookCopy(@RequestBody BookCopyRequestDTO requestDTO) {
        BookCopyDTO created = service.add(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // Cập nhật bản sao sách
    @PutMapping("/{id}")
    public ResponseEntity<BookCopyDTO> updateBookCopy(
            @PathVariable Long id,
            @RequestBody BookCopyRequestDTO requestDTO) {
        BookCopyDTO updated = service.update(id, requestDTO);
        return ResponseEntity.ok(updated);
    }

    // Xóa (chuyển trạng thái thành INACTIVE) bản sao sách
    @DeleteMapping("/{id}")
    public ResponseEntity<BookCopyDTO> deleteBookCopy(@PathVariable Long id) {
        BookCopyDTO deleted = service.delete(id);
        return ResponseEntity.ok(deleted);
    }

    // Lấy bản sao sách theo bookId
    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<BookCopyDTO>> getBookCopiesByBookId(@PathVariable Long bookId) {
        List<BookCopyDTO> bookCopies = service.getBookCopiesByBookId(bookId);
        return ResponseEntity.ok(bookCopies);
    }

    // Lấy bản sao sách theo shelfId
    @GetMapping("/shelf/{shelfId}")
    public ResponseEntity<List<BookCopyDTO>> getBookCopiesByShelfId(@PathVariable Long shelfId) {
        List<BookCopyDTO> bookCopies = service.getBookCopiesByShelfId(shelfId);
        return ResponseEntity.ok(bookCopies);
    }

    // Lấy các bản sao sách đang hoạt động
    @GetMapping("/active")
    public ResponseEntity<List<BookCopyDTO>> getActiveBookCopies() {
        List<BookCopyDTO> bookCopies = service.getActiveBookCopies();
        return ResponseEntity.ok(bookCopies);
    }

    // Lấy bản sao sách theo barcode
    @GetMapping("/barcode/{barcode}")
    public ResponseEntity<BookCopyDTO> getBookCopyByBarcode(@PathVariable String barcode) {
        BookCopyDTO bookCopy = service.getBookCopyByBarcode(barcode);
        return ResponseEntity.ok(bookCopy);
    }

    // Lấy bản sao sách đang hoạt động theo bookId
    @GetMapping("/book/{bookId}/active")
    public ResponseEntity<List<BookCopyDTO>> getActiveBookCopiesByBookId(@PathVariable Long bookId) {
        List<BookCopyDTO> bookCopies = service.getActiveBookCopiesByBookId(bookId);
        return ResponseEntity.ok(bookCopies);
    }

    // Kiểm tra barcode đã tồn tại chưa
    @GetMapping("/check-barcode")
    public ResponseEntity<Boolean> checkBarcodeExists(@RequestParam String barcode) {
        boolean exists = service.checkBarcodeExists(barcode);
        return ResponseEntity.ok(exists);
    }

    // Đếm số lượng bản sao theo bookId
    @GetMapping("/count/book/{bookId}")
    public ResponseEntity<Long> countByBookId(@PathVariable Long bookId) {
        long count = service.countByBookId(bookId);
        return ResponseEntity.ok(count);
    }

    // Đếm số lượng bản sao đang hoạt động theo bookId
    @GetMapping("/count/book/{bookId}/active")
    public ResponseEntity<Long> countActiveByBookId(@PathVariable Long bookId) {
        long count = service.countActiveByBookId(bookId);
        return ResponseEntity.ok(count);
    }
}