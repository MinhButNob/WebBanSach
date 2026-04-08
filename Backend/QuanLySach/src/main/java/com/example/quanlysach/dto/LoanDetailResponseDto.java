package com.example.quanlysach.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanDetailResponseDto {
    private Long id;
    private Long loanId;
    private LocalDate loanDate;
    private LocalDate dueDate;
    private Long bookId;
    private String bookTitle;
    private String status;
}

