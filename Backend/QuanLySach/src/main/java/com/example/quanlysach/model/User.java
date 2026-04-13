package com.example.quanlysach.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "email", length = 100, unique = true)
    private String email;

    @Column(name = "password", length = 255)  // Thêm trường password
    private String password;

    @Column(name = "status", length = 20)
    private String status;

    @Column(name = "membership_level", length = 50)
    private String membershipLevel;

    @Column(name = "max_loan_limit")
    private Integer maxLoanLimit;

    @Column(name = "current_loan_count")
    private Integer currentLoanCount;

    @Column(name = "overdue_count")
    private Integer overdueCount;

    @Column(name = "reservation_limit")
    private Integer reservationLimit;

    @Column(name = "penalty_balance", precision = 10, scale = 2)
    private BigDecimal penaltyBalance;

    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "membership_id")
    private Long membershipId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "role_id", insertable = false, updatable = false)
    private Role role;

    @ManyToOne
    @JoinColumn(name = "membership_id", insertable = false, updatable = false)
    private Membership membership;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (status == null) status = "ACTIVE";
        if (currentLoanCount == null) currentLoanCount = 0;
        if (overdueCount == null) overdueCount = 0;
        if (penaltyBalance == null) penaltyBalance = BigDecimal.ZERO;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
