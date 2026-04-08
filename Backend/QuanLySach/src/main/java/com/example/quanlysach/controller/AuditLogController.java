package com.example.quanlysach.controller;

import com.example.quanlysach.model.AuditLog;
import com.example.quanlysach.service.AuditLogService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audit-logs")
@CrossOrigin("*")
public class AuditLogController {
    private final AuditLogService service;

    public AuditLogController(AuditLogService service) {
        this.service = service;
    }

    @GetMapping
    public List<AuditLog> getAllAuditLogs() {
        return service.getAllAuditLogs();
    }

    @GetMapping("/{id}")
    public AuditLog getAuditLogById(@PathVariable Long id) {
        return service.getAuditLogById(id);
    }

    @PostMapping
    public AuditLog addAuditLog(@RequestBody AuditLog auditLog) {
        return service.add(auditLog);
    }
}

