package com.example.quanlysach.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "book_categories")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookCategory {
    @EmbeddedId
    private BookCategoryId id;

    @ManyToOne
    @JoinColumn(name = "book_id", insertable = false, updatable = false)
    private Book book;

    @ManyToOne
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    private Category category;
}

