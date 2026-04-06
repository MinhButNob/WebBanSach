package com.example.quanlysach.repository;

import com.example.quanlysach.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long> {
}

