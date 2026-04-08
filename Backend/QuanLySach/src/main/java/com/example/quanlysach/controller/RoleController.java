package com.example.quanlysach.controller;

import com.example.quanlysach.model.Role;
import com.example.quanlysach.service.RoleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@CrossOrigin("*")
public class RoleController {
    private final RoleService service;

    public RoleController(RoleService service) {
        this.service = service;
    }

    @GetMapping
    public List<Role> getAllRoles() {
        return service.getAllRoles();
    }
    @PostMapping
    public Role addRole(@RequestBody Role role) {
        return service.add(role);
    }

    @PostMapping("/{id}")
    public Role updateRole(@PathVariable Long id, @RequestBody Role role) {
        return service.update(id, role);
    }

    @DeleteMapping("/{id}")
    public Role deleteRole(@PathVariable Long id) {
        return service.delete(id);
    }
}
