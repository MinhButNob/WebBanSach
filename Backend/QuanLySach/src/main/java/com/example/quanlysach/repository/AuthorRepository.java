package com.example.quanlysach.repository;

import com.example.quanlysach.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}

