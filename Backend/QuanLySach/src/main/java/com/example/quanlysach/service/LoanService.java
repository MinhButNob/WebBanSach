package com.example.quanlysach.service;

import com.example.quanlysach.model.Loan;
import com.example.quanlysach.repository.LoanRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanService {
    private final LoanRepository loanRepo;

    public LoanService(LoanRepository loanRepo) {
        this.loanRepo = loanRepo;
    }

    public List<Loan> getAllLoans() {
        return loanRepo.findAll();
    }

    public Loan getLoanById(Long id) {
        return loanRepo.findById(id).orElseThrow();
    }

    public Loan add(Loan loan) {
        return loanRepo.save(loan);
    }

    public Loan update(Long id, Loan loan) {
        Loan l = loanRepo.findById(id).orElseThrow();
        l.setUserId(loan.getUserId());
        l.setLoanDate(loan.getLoanDate());
        l.setDueDate(loan.getDueDate());
        l.setReturnDate(loan.getReturnDate());
        l.setStatus(loan.getStatus());
        l.setTotalBooks(loan.getTotalBooks());
        l.setOverdueDays(loan.getOverdueDays());
        l.setFineAmount(loan.getFineAmount());
        l.setReminderSent(loan.getReminderSent());
        return loanRepo.save(l);
    }

    public Loan delete(Long id) {
        Loan l = loanRepo.findById(id).orElseThrow();
        l.setStatus("INACTIVE");
        return loanRepo.save(l);
    }
}

