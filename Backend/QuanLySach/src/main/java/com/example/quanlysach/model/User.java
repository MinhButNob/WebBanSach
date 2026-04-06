package com.example.quanlysach.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "status")
    private String status;

    @Column(name = "membership_level")
    private String membershipLevel;

    @Column(name = "max_loan_limit")
    private Integer maxLoanLimit;

    @Column(name = "current_loan_count")
    private Integer currentLoanCount;

    @Column(name = "overdue_count")
    private Integer overdueCount;

    @Column(name = "reservation_limit")
    private Integer reservationLimit;

    @Column(name = "penalty_balance")
    private Double penaltyBalance;

    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "membership_id")
    private Long membershipId;

    @ManyToOne
    @JoinColumn(name = "role_id", insertable = false, updatable = false)
    private Role role;

    @ManyToOne
    @JoinColumn(name = "membership_id", insertable = false, updatable = false)
    private MemberShip memberShip;
}
