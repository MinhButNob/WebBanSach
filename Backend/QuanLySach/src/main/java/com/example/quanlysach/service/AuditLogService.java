package com.example.quanlysach.service;

import com.example.quanlysach.model.AuditLog;
import com.example.quanlysach.repository.AuditLogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuditLogService {
    private final AuditLogRepository auditLogRepo;

    public AuditLogService(AuditLogRepository auditLogRepo) {
        this.auditLogRepo = auditLogRepo;
    }

    public List<AuditLog> getAllAuditLogs() {
        return auditLogRepo.findAll();
    }

    public AuditLog getAuditLogById(Long id) {
        return auditLogRepo.findById(id).orElseThrow();
    }

    public AuditLog add(AuditLog auditLog) {
        return auditLogRepo.save(auditLog);
    }
}

