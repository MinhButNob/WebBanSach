package com.example.quanlysach.service;

import com.example.quanlysach.model.User;
import com.example.quanlysach.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public User getUserById(Long id) {
        return userRepo.findById(id).orElseThrow();
    }

    public User add(User user) {
        return userRepo.save(user);
    }

    public User update(Long id, User user) {
        User u = userRepo.findById(id).orElseThrow();
        u.setName(user.getName());
        u.setEmail(user.getEmail());
        u.setStatus(user.getStatus());
        u.setMembershipLevel(user.getMembershipLevel());
        u.setMaxLoanLimit(user.getMaxLoanLimit());
        u.setCurrentLoanCount(user.getCurrentLoanCount());
        u.setOverdueCount(user.getOverdueCount());
        u.setReservationLimit(user.getReservationLimit());
        u.setPenaltyBalance(user.getPenaltyBalance());
        u.setRoleId(user.getRoleId());
        u.setMembershipId(user.getMembershipId());
        return userRepo.save(u);
    }

    public User delete(Long id) {
        User u = userRepo.findById(id).orElseThrow();
        u.setStatus("INACTIVE");
        return userRepo.save(u);
    }
}

