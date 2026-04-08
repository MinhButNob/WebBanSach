package com.example.quanlysach.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String status;
    private String membershipLevel;
    private Integer maxLoanLimit;
    private Integer currentLoanCount;
    private Integer overdueCount;
    private Integer reservationLimit;
    private Double penaltyBalance;
    private Long roleId;
    private Long membershipId;
}

