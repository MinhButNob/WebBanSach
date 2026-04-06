package com.example.quanlysach.repository;

import com.example.quanlysach.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}

