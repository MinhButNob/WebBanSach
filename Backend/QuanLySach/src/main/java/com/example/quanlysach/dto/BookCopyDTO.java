package com.example.quanlysach.dto;

import com.example.quanlysach.model.BookCopy;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookCopyDTO {
    private Long id;
    private Long bookId;
    private String barcode;
    private String status;
    private Long shelfId;

    // Thông tin từ Book entity
    private String bookTitle;
    private String bookIsbn;
    private String bookLanguage;
    private String bookEdition;
    private Integer bookTotalCopies;
    private Integer bookAvailableCopies;
    private String bookStatus;

    // Thông tin từ Shelf entity
    private String shelfName;
    private String shelfLocation;
    private String shelfStatus;

    // Thông tin từ Publisher (thông qua Book)
    private String publisherName;

    private List<String> categories;

    // Constructor chuyển từ Entity sang DTO
    public BookCopyDTO(BookCopy bookCopy) {
        this.id = bookCopy.getId();
        this.bookId = bookCopy.getBookId();
        this.barcode = bookCopy.getBarcode();
        this.status = bookCopy.getStatus();
        this.shelfId = bookCopy.getShelfId();

        // Lấy thông tin từ Book nếu có
        if (bookCopy.getBook() != null) {
            com.example.quanlysach.model.Book book = bookCopy.getBook();
            this.bookTitle = book.getTitle();
            this.bookIsbn = book.getIsbn();
            this.bookLanguage = book.getLanguage();
            this.bookEdition = book.getEdition();
            this.bookTotalCopies = book.getTotalCopies();
            this.bookAvailableCopies = book.getAvailableCopies();
            this.bookStatus = book.getStatus();

            // Lấy thông tin Publisher nếu có
            if (book.getPublisher() != null) {
                this.publisherName = book.getPublisher().getName();
            }
        }

        // Lấy thông tin từ Shelf nếu có
        if (bookCopy.getShelf() != null) {
            this.shelfName = bookCopy.getShelf().getName();
            this.shelfLocation = bookCopy.getShelf().getLocation();
            this.shelfStatus = bookCopy.getShelf().getStatus();
        }
    }
}