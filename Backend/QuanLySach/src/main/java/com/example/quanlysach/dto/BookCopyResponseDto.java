package com.example.quanlysach.dto;

import com.example.quanlysach.model.BookCopy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookCopyResponseDTO {
    private Long id;
    private String barcode;
    private String status;
    private Long bookId;
    private String bookTitle;
    private Long shelfId;
    private String shelfName;
    private String shelfLocation;

    public BookCopyResponseDTO(BookCopy bookCopy) {
        this.id = bookCopy.getId();
        this.barcode = bookCopy.getBarcode();
        this.status = bookCopy.getStatus();
        this.bookId = bookCopy.getBookId();
        this.shelfId = bookCopy.getShelfId();

        if (bookCopy.getBook() != null) {
            this.bookTitle = bookCopy.getBook().getTitle();
        }
        if (bookCopy.getShelf() != null) {
            this.shelfName = bookCopy.getShelf().getName();
            this.shelfLocation = bookCopy.getShelf().getLocation();
        }
    }
}

