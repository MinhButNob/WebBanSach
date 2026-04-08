package com.example.quanlysach.service;

import com.example.quanlysach.model.Payment;
import com.example.quanlysach.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepo;

    public PaymentService(PaymentRepository paymentRepo) {
        this.paymentRepo = paymentRepo;
    }

    public List<Payment> getAllPayments() {
        return paymentRepo.findAll();
    }

    public Payment getPaymentById(Long id) {
        return paymentRepo.findById(id).orElseThrow();
    }

    public Payment add(Payment payment) {
        return paymentRepo.save(payment);
    }

    public Payment update(Long id, Payment payment) {
        Payment p = paymentRepo.findById(id).orElseThrow();
        p.setFineId(payment.getFineId());
        p.setAmount(payment.getAmount());
        p.setPaymentDate(payment.getPaymentDate());
        p.setMethod(payment.getMethod());
        p.setReceiptNumber(payment.getReceiptNumber());
        p.setStatus(payment.getStatus());
        return paymentRepo.save(p);
    }

    public Payment delete(Long id) {
        Payment p = paymentRepo.findById(id).orElseThrow();
        p.setStatus("INACTIVE");
        return paymentRepo.save(p);
    }
}

