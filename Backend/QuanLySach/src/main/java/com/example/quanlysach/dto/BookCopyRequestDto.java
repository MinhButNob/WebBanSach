package com.example.quanlysach.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookCopyRequestDTO {
    private Long bookId;
    private String barcode;
    private String status;
    private Long shelfId;
}

