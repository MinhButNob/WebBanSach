package com.example.quanlysach.controller;

import com.example.quanlysach.model.Membership;
import com.example.quanlysach.service.MembershipService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/memberships")
@CrossOrigin("*")
public class MembershipController {
    private final MembershipService service;

    public MembershipController(MembershipService service) {
        this.service = service;
    }

    @GetMapping
    public List<Membership> getAllMemberShip() {
        return service.getAllMemberShip();
    }

    @GetMapping("/{id}")
    public Membership getMemberShipById(@PathVariable Long id) {
        return service.getMemberShipById(id);
    }

    @PostMapping
    public Membership addMemberShip(@RequestBody Membership memberShip) {
        return service.add(memberShip);
    }

    @PutMapping("/{id}")
    public Membership updateMemberShip(@PathVariable Long id, @RequestBody Membership memberShip) {
        return service.update(id, memberShip);
    }

    @DeleteMapping("/{id}")
    public Membership deleteMemberShip(@PathVariable Long id) {
        return service.delete(id);
    }
}

