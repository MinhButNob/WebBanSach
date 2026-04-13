package com.example.quanlysach.repository;

import com.example.quanlysach.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByTitle(String title);

    List<Book> findByStatus(String status);
    List<Book> findByPublisherId(Long publisherId);
    List<Book> findByTitleContainingIgnoreCase(String title);
    // Tìm sách theo tên thể loại
    @Query("SELECT b FROM Book b WHERE b.id IN " +
            "(SELECT bc.book.id FROM BookCategory bc WHERE bc.category.name = :categoryName AND bc.status = 'ACTIVE')")
    List<Book> findBooksByCategoryName(@Param("categoryName") String categoryName);
}

