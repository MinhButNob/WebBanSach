package com.example.quanlysach.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "loan_details")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoanDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "loan_id")
    private Long loanId;

    @Column(name = "book_id")
    private Long bookId;

    @ManyToOne
    @JoinColumn(name = "loan_id", insertable = false, updatable = false)
    private Loan loan;

    @ManyToOne
    @JoinColumn(name = "book_id", insertable = false, updatable = false)
    private Book book;
}

