package com.example.quanlysach.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookAuthorRequestDto {
    private Long bookId;
    private Long authorId;
    private String status;
}

