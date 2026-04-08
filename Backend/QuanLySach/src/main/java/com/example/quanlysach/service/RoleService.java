package com.example.quanlysach.service;

import com.example.quanlysach.model.Role;
import com.example.quanlysach.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    private final RoleRepository roleRepo;

    public RoleService(RoleRepository roleRepo) {
        this.roleRepo = roleRepo;
    }

    public List<Role> getAllRoles() {
        return roleRepo.findAll();
    }

    public Role getRoleById(Long id) {
        return roleRepo.findById(id).orElseThrow();
    }

    public Role add(Role role) {
        return roleRepo.save(role);
    }

    public Role update(Long id, Role role) {
        Role r = roleRepo.findById(id).orElseThrow();
        r.setName(role.getName());
        r.setPermissionLevel(role.getPermissionLevel());
        r.setMaxReservationLimit(role.getMaxReservationLimit());
        return roleRepo.save(r);
    }

    public Role delete(Long id) {
        Role r = roleRepo.findById(id).orElseThrow();
        roleRepo.delete(r);
        return r;
    }
}
