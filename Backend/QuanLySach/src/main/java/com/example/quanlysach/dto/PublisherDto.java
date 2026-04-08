package com.example.quanlysach.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublisherDto {
    private Long id;
    private String name;
    private Integer totalPublishedBooks;
    private Integer averageLoanDays;
    private String status;
}

