package com.example.quanlysach.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookCategoryResponseDto {
    private Long bookId;
    private String bookTitle;
    private Long categoryId;
    private String categoryName;
    private String status;
}

