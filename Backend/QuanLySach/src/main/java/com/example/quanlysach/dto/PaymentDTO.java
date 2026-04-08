package com.example.quanlysach.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {
    private Long id;
    private Long fineId;
    private BigDecimal amount;
    private LocalDate paymentDate;
    private String method;
    private String receiptNumber;
    private String status;
}

