package com.example.quanlysach.repository;

import com.example.quanlysach.model.Membership;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberShipRepository extends JpaRepository<Membership, Long> {
}

