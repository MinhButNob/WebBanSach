package com.example.quanlysach.repository;

import com.example.quanlysach.model.BookCopy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookCopyRepository extends JpaRepository<BookCopy, Long> {
    // Tìm theo bookId
    List<BookCopy> findByBookId(Long bookId);

    List<BookCopy> findByShelfId(Long shelfId);

    List<BookCopy> findByStatus(String status);

    Optional<BookCopy> findByBarcode(String barcode);

    boolean existsByBarcode(String barcode);

    List<BookCopy> findByBookIdAndStatus(Long bookId, String status);

    List<BookCopy> findByShelfIdAndStatus(Long shelfId, String status);

    long countByBookIdAndStatus(Long bookId, String status);

}

