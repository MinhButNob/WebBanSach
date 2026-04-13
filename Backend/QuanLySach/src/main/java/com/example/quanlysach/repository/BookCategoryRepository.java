package com.example.quanlysach.repository;

import com.example.quanlysach.model.BookCategory;
import com.example.quanlysach.model.BookCategoryId;
import com.example.quanlysach.model.BookCopy;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookCategoryRepository extends JpaRepository<BookCategory, BookCategoryId> {
//    List<BookCategory> findByCategory_Id(Long categoryId);
//
//    // Tương tự cho book
//    List<BookCategory> findByBook_Id(Long bookId);
//
    // Tìm theo book_id và status
    List<BookCategory> findByBook_IdAndStatus(Long bookId, String status);

    // Xóa theo book_id
    void deleteByBook_Id(Long bookId);

//    // Tìm theo categoryId và status
//    List<BookCategory> findByIdCategoryIdAndStatus(Long categoryId, String status);
//    @Query("SELECT bc.category.name FROM BookCategory bc WHERE bc.book.id = :bookId AND bc.status = 'ACTIVE'")
//    List<String> findCategoryNamesByBookId(@Param("bookId") Long bookId);

    // CÁCH 1: Dùng @Query - Khuyến nghị, tránh lỗi
    @Query("SELECT bc FROM BookCategory bc WHERE bc.book.id = :bookId")
    List<BookCategory> findByBookId(@Param("bookId") Long bookId);

    @Query("SELECT bc FROM BookCategory bc WHERE bc.category.id = :categoryId")
    List<BookCategory> findByCategoryId(@Param("categoryId") Long categoryId);

    @Query("SELECT bc FROM BookCategory bc WHERE bc.book.id = :bookId AND bc.status = :status")
    List<BookCategory> findByBookIdAndStatus(@Param("bookId") Long bookId, @Param("status") String status);

    @Query("SELECT bc FROM BookCategory bc WHERE bc.category.id = :categoryId AND bc.status = :status")
    List<BookCategory> findByCategoryIdAndStatus(@Param("categoryId") Long categoryId, @Param("status") String status);

    @Modifying
    @Transactional
    @Query("DELETE FROM BookCategory bc WHERE bc.book.id = :bookId")
    void deleteByBookId(@Param("bookId") Long bookId);

    @Query("SELECT bc.category.name FROM BookCategory bc WHERE bc.book.id = :bookId AND bc.status = 'ACTIVE'")
    List<String> findCategoryNamesByBookId(@Param("bookId") Long bookId);

    @Query("SELECT bc FROM BookCategory bc WHERE bc.status = :status")
    List<BookCategory> findByStatus(@Param("status") String status);

}

