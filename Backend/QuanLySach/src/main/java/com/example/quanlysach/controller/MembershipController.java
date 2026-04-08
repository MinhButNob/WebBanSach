package com.example.quanlysach.controller;

import com.example.quanlysach.model.MemberShip;
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
    public List<MemberShip> getAllMemberShip() {
        return service.getAllMemberShip();
    }

    @GetMapping("/{id}")
    public MemberShip getMemberShipById(@PathVariable Long id) {
        return service.getMemberShipById(id);
    }

    @PostMapping
    public MemberShip addMemberShip(@RequestBody MemberShip memberShip) {
        return service.add(memberShip);
    }

    @PostMapping("/{id}")
    public MemberShip updateMemberShip(@PathVariable Long id, @RequestBody MemberShip memberShip) {
        return service.update(id, memberShip);
    }

    @DeleteMapping("/{id}")
    public MemberShip deleteMemberShip(@PathVariable Long id) {
        return service.delete(id);
    }
}

