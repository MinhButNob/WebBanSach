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
public class FineDto {
    private Long id;
    private Long loanId;
    private BigDecimal amount;
    private String reason;
    private LocalDate issuedDate;
    private LocalDate dueDate;
    private String status;
}

