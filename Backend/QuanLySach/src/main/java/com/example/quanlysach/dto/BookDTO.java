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

    public BookDTO(Long id, String title, String isbn, String language, String edition, Integer totalCopies, Integer availableCopies, Integer minLoanDays, Integer maxLoanDays, Integer popularityScore, String status, Long aLong, String s) {

        this.id = id;
        this.title = title;
        this.isbn = isbn;
        this.language = language;
        this.edition = edition;
        this.totalCopies = totalCopies;
        this.availableCopies = availableCopies;
        this.minLoanDays = minLoanDays;
        this.maxLoanDays = maxLoanDays;
        this.popularityScore = popularityScore;
        this.status = status;
        this.publisherId = aLong;
    }
}

