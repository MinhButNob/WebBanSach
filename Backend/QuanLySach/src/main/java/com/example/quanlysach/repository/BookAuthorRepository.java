package com.example.quanlysach.repository;

import com.example.quanlysach.model.BookAuthor;
import com.example.quanlysach.model.BookAuthorId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookAuthorRepository extends JpaRepository<BookAuthor, BookAuthorId> {
}

