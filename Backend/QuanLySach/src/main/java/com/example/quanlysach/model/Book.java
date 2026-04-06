package com.example.quanlysach.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "books")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", length = 255)
    private String title;

    @Column(name = "isbn", length = 50)
    private String isbn;

    @Column(name = "language", length = 50)
    private String language;

    @Column(name = "edition", length = 50)
    private String edition;

    @Column(name = "total_copies")
    private Integer totalCopies;

    @Column(name = "available_copies")
    private Integer availableCopies;

    @Column(name = "min_loan_days")
    private Integer minLoanDays;

    @Column(name = "max_loan_days")
    private Integer maxLoanDays;

    @Column(name = "popularity_score")
    private Integer popularityScore;

    @Column(name = "publisher_id")
    private Long publisherId;

    @ManyToOne
    @JoinColumn(name = "publisher_id", insertable = false, updatable = false)
    private Publisher publisher;
}
