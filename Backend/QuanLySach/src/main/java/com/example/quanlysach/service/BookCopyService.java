package com.example.quanlysach.service;

import com.example.quanlysach.dto.BookCopyDTO;
import com.example.quanlysach.dto.BookCopyRequestDTO;
import com.example.quanlysach.model.BookCopy;
import com.example.quanlysach.repository.BookCopyRepository;
import com.example.quanlysach.repository.BookRepository;
import com.example.quanlysach.repository.ShelfRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;


import java.util.stream.Collectors;

@Service
public class BookCopyService {
    private final BookCopyRepository bookCopyRepo;
    private final BookRepository bookRepo;
    private final ShelfRepository shelfRepo;

    public BookCopyService(BookCopyRepository bookCopyRepo,
                           BookRepository bookRepo,
                           ShelfRepository shelfRepo) {
        this.bookCopyRepo = bookCopyRepo;
        this.bookRepo = bookRepo;
        this.shelfRepo = shelfRepo;
    }

    public List<BookCopyDTO> getAllBookCopies() {
        return bookCopyRepo.findAll().stream()
                .map(BookCopyDTO::new)
                .collect(Collectors.toList());
    }

    public BookCopyDTO getBookCopyById(Long id) {
        BookCopy bookCopy = bookCopyRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bản sao sách với id: " + id));
        return new BookCopyDTO(bookCopy);
    }

    @Transactional
    public BookCopyDTO add(BookCopyRequestDTO requestDTO) {
        // Validate book exists
        if (!bookRepo.existsById(requestDTO.getBookId())) {
            throw new RuntimeException("Không tìm thấy sách với id: " + requestDTO.getBookId());
        }

        // Validate shelf exists (if shelfId is provided)
        if (requestDTO.getShelfId() != null && !shelfRepo.existsById(requestDTO.getShelfId())) {
            throw new RuntimeException("Không tìm thấy kệ với id: " + requestDTO.getShelfId());
        }

        // Check barcode uniqueness
        if (bookCopyRepo.existsByBarcode(requestDTO.getBarcode())) {
            throw new RuntimeException("Mã vạch đã tồn tại: " + requestDTO.getBarcode());
        }

        BookCopy bookCopy = new BookCopy();
        bookCopy.setBookId(requestDTO.getBookId());
        bookCopy.setBarcode(requestDTO.getBarcode());
        bookCopy.setStatus(requestDTO.getStatus() != null ? requestDTO.getStatus() : "ACTIVE");
        bookCopy.setShelfId(requestDTO.getShelfId());

        BookCopy saved = bookCopyRepo.save(bookCopy);

        // Update total copies in Book
        bookRepo.findById(requestDTO.getBookId()).ifPresent(book -> {
            if (book.getTotalCopies() != null) {
                book.setTotalCopies(book.getTotalCopies() + 1);
            } else {
                book.setTotalCopies(1);
            }
            if (book.getAvailableCopies() != null) {
                book.setAvailableCopies(book.getAvailableCopies() + 1);
            } else {
                book.setAvailableCopies(1);
            }
            bookRepo.save(book);
        });

        return new BookCopyDTO(saved);
    }

    @Transactional
    public BookCopyDTO update(Long id, BookCopyRequestDTO requestDTO) {
        BookCopy bc = bookCopyRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bản sao sách với id: " + id));

        // Check barcode uniqueness for other records
        if (!bc.getBarcode().equals(requestDTO.getBarcode()) &&
                bookCopyRepo.existsByBarcode(requestDTO.getBarcode())) {
            throw new RuntimeException("Mã vạch đã tồn tại: " + requestDTO.getBarcode());
        }

        bc.setBookId(requestDTO.getBookId());
        bc.setBarcode(requestDTO.getBarcode());
        bc.setStatus(requestDTO.getStatus());
        bc.setShelfId(requestDTO.getShelfId());

        BookCopy updated = bookCopyRepo.save(bc);
        return new BookCopyDTO(updated);
    }

    @Transactional
    public BookCopyDTO delete(Long id) {
        BookCopy bc = bookCopyRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bản sao sách với id: " + id));

        bc.setStatus("INACTIVE");
        BookCopy updated = bookCopyRepo.save(bc);

        // Update available copies in Book
        bookRepo.findById(bc.getBookId()).ifPresent(book -> {
            if (book.getAvailableCopies() != null && book.getAvailableCopies() > 0) {
                book.setAvailableCopies(book.getAvailableCopies() - 1);
                bookRepo.save(book);
            }
        });

        return new BookCopyDTO(updated);
    }

    // Các method bổ sung
    public List<BookCopyDTO> getBookCopiesByBookId(Long bookId) {
        return bookCopyRepo.findByBookId(bookId).stream()
                .map(BookCopyDTO::new)
                .collect(Collectors.toList());
    }

    public List<BookCopyDTO> getBookCopiesByShelfId(Long shelfId) {
        return bookCopyRepo.findByShelfId(shelfId).stream()
                .map(BookCopyDTO::new)
                .collect(Collectors.toList());
    }

    public List<BookCopyDTO> getActiveBookCopies() {
        return bookCopyRepo.findByStatus("ACTIVE").stream()
                .map(BookCopyDTO::new)
                .collect(Collectors.toList());
    }

    public BookCopyDTO getBookCopyByBarcode(String barcode) {
        BookCopy bookCopy = bookCopyRepo.findByBarcode(barcode)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bản sao sách với barcode: " + barcode));
        return new BookCopyDTO(bookCopy);
    }

    public List<BookCopyDTO> getActiveBookCopiesByBookId(Long bookId) {
        return bookCopyRepo.findByBookIdAndStatus(bookId, "ACTIVE").stream()
                .map(BookCopyDTO::new)
                .collect(Collectors.toList());
    }

    public boolean checkBarcodeExists(String barcode) {
        return bookCopyRepo.existsByBarcode(barcode);
    }

    public long countByBookId(Long bookId) {
        return bookCopyRepo.findByBookId(bookId).size();
    }

    public long countActiveByBookId(Long bookId) {
        return bookCopyRepo.countByBookIdAndStatus(bookId, "ACTIVE");
    }
}