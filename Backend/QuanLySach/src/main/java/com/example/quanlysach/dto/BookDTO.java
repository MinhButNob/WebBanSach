package com.example.quanlysach.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    private Long id;
    private String title;
    private String isbn;
    private String language;
    private String edition;
    private Integer totalCopies;
    private Integer availableCopies;
    private Integer minLoanDays;
    private Integer maxLoanDays;
    private Integer popularityScore;
    private Long publisherId;
    private String status;
}

