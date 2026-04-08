package com.example.quanlysach.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookAuthorResponseDto {
    private Long bookId;
    private String bookTitle;
    private Long authorId;
    private String authorName;
    private String status;
}

