package com.example.quanlysach.repository;


import com.example.quanlysach.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    List<User> findByStatus(String status);

    List<User> findByRoleId(Long roleId);

    List<User> findByMembershipLevel(String membershipLevel);

    Optional<User> findByEmailAndStatus(String email, String status);
}