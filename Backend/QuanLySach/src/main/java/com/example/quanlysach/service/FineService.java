package com.example.quanlysach.service;

import com.example.quanlysach.model.Fine;
import com.example.quanlysach.repository.FineRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FineService {
    private final FineRepository fineRepo;

    public FineService(FineRepository fineRepo) {
        this.fineRepo = fineRepo;
    }

    public List<Fine> getAllFines() {
        return fineRepo.findAll();
    }

    public Fine getFineById(Long id) {
        return fineRepo.findById(id).orElseThrow();
    }

    public Fine add(Fine fine) {
        return fineRepo.save(fine);
    }

    public Fine update(Long id, Fine fine) {
        Fine f = fineRepo.findById(id).orElseThrow();
        f.setLoanId(fine.getLoanId());
        f.setAmount(fine.getAmount());
        f.setReason(fine.getReason());
        f.setIssuedDate(fine.getIssuedDate());
        f.setDueDate(fine.getDueDate());
        f.setStatus(fine.getStatus());
        return fineRepo.save(f);
    }

    public Fine delete(Long id) {
        Fine f = fineRepo.findById(id).orElseThrow();
        f.setStatus("INACTIVE");
        return fineRepo.save(f);
    }
}

