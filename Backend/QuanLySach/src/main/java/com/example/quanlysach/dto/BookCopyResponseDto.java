package com.example.quanlysach.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookCopyResponseDto {
    private Long id;
    private Long bookId;
    private String bookTitle;
    private String barcode;
    private String status;
    private Long shelfId;
    private String shelfName;
    private String shelfLocation;
}

