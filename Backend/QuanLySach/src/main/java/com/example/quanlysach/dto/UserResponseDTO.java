package com.example.quanlysach.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String status;
    private String membershipLevel;
    private Integer maxLoanLimit;
    private Integer currentLoanCount;
    private Integer overdueCount;
    private Integer reservationLimit;
    private BigDecimal penaltyBalance;
    private String roleName;
    private String token;
}