package com.example.quanlysach.service;

import com.example.quanlysach.model.LoanDetail;
import com.example.quanlysach.repository.LoanDetailRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanDetailService {
    private final LoanDetailRepository loanDetailRepo;

    public LoanDetailService(LoanDetailRepository loanDetailRepo) {
        this.loanDetailRepo = loanDetailRepo;
    }

    public List<LoanDetail> getAllLoanDetails() {
        return loanDetailRepo.findAll();
    }

    public LoanDetail getLoanDetailById(Long id) {
        return loanDetailRepo.findById(id).orElseThrow();
    }

    public LoanDetail add(LoanDetail loanDetail) {
        return loanDetailRepo.save(loanDetail);
    }

    public LoanDetail update(Long id, LoanDetail loanDetail) {
        LoanDetail ld = loanDetailRepo.findById(id).orElseThrow();
        ld.setLoanId(loanDetail.getLoanId());
        ld.setBookId(loanDetail.getBookId());
        ld.setStatus(loanDetail.getStatus());
        return loanDetailRepo.save(ld);
    }

    public LoanDetail delete(Long id) {
        LoanDetail ld = loanDetailRepo.findById(id).orElseThrow();
        ld.setStatus("INACTIVE");
        return loanDetailRepo.save(ld);
    }
}


