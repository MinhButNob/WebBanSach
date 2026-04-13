package com.example.quanlysach.service;

import com.example.quanlysach.dto.LoginRequestDTO;
import com.example.quanlysach.dto.RegisterRequestDTO;
import com.example.quanlysach.dto.UserResponseDTO;
import com.example.quanlysach.model.Role;
import com.example.quanlysach.model.User;
import com.example.quanlysach.repository.RoleRepository;
import com.example.quanlysach.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Base64;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    // Đăng nhập
    public UserResponseDTO login(LoginRequestDTO loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Email không tồn tại"));

        if (!user.getPassword().equals(encodePassword(loginRequest.getPassword()))) {
            throw new RuntimeException("Mật khẩu không đúng");
        }

        if (!"ACTIVE".equals(user.getStatus())) {
            throw new RuntimeException("Tài khoản đã bị khóa");
        }

        String token = generateToken(user);

        return UserResponseDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .status(user.getStatus())
                .membershipLevel(user.getMembershipLevel())
                .maxLoanLimit(user.getMaxLoanLimit())
                .currentLoanCount(user.getCurrentLoanCount())
                .overdueCount(user.getOverdueCount())
                .reservationLimit(user.getReservationLimit())
                .penaltyBalance(user.getPenaltyBalance())
                .roleName(user.getRole() != null ? user.getRole().getName() : null)
                .token(token)
                .build();
    }

    // Đăng ký
    @Transactional
    public UserResponseDTO register(RegisterRequestDTO registerRequest) {
        // Kiểm tra email đã tồn tại
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new RuntimeException("Email đã được đăng ký");
        }

        // Tạo user mới
        User user = new User();
        user.setName(registerRequest.getName());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(encodePassword(registerRequest.getPassword()));
        user.setStatus("ACTIVE");
        user.setMembershipLevel(registerRequest.getMembershipLevel() != null ?
                registerRequest.getMembershipLevel() : "STANDARD");

        // Set limits theo membership level
        if ("PREMIUM".equals(user.getMembershipLevel())) {
            user.setMaxLoanLimit(10);
            user.setReservationLimit(5);
        } else {
            user.setMaxLoanLimit(5);
            user.setReservationLimit(3);
        }

        user.setCurrentLoanCount(0);
        user.setOverdueCount(0);
        user.setPenaltyBalance(BigDecimal.ZERO);

        // Gán role mặc định là USER (role_id = 1)
        user.setRoleId(1L);

        User savedUser = userRepository.save(user);
        String token = generateToken(savedUser);

        return UserResponseDTO.builder()
                .id(savedUser.getId())
                .name(savedUser.getName())
                .email(savedUser.getEmail())
                .status(savedUser.getStatus())
                .membershipLevel(savedUser.getMembershipLevel())
                .maxLoanLimit(savedUser.getMaxLoanLimit())
                .currentLoanCount(savedUser.getCurrentLoanCount())
                .overdueCount(savedUser.getOverdueCount())
                .reservationLimit(savedUser.getReservationLimit())
                .penaltyBalance(savedUser.getPenaltyBalance())
                .roleName(savedUser.getRole() != null ? savedUser.getRole().getName() : "USER")
                .token(token)
                .build();
    }

    // Lấy thông tin user theo ID
    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy user"));

        return UserResponseDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .status(user.getStatus())
                .membershipLevel(user.getMembershipLevel())
                .maxLoanLimit(user.getMaxLoanLimit())
                .currentLoanCount(user.getCurrentLoanCount())
                .overdueCount(user.getOverdueCount())
                .reservationLimit(user.getReservationLimit())
                .penaltyBalance(user.getPenaltyBalance())
                .roleName(user.getRole() != null ? user.getRole().getName() : null)
                .build();
    }

    // Mã hóa mật khẩu
    private String encodePassword(String password) {
        return Base64.getEncoder().encodeToString(password.getBytes());
    }

    // Tạo token đơn giản
    private String generateToken(User user) {
        String tokenData = user.getId() + ":" + user.getEmail() + ":" + System.currentTimeMillis();
        return Base64.getEncoder().encodeToString(tokenData.getBytes());
    }
}