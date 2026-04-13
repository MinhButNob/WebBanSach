package com.example.quanlysach.service;

import com.example.quanlysach.model.Membership;
import com.example.quanlysach.repository.MemberShipRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MembershipService {
    private final MemberShipRepository memberRepo;
    public MembershipService(MemberShipRepository memberRepo) {
        this.memberRepo = memberRepo;
    }

    public List<Membership> getAllMemberShip() {
        return memberRepo.findAll();
    }

    public Membership getMemberShipById(Long id) {
        return memberRepo.findById(id).orElseThrow();

    }

    public Membership add(Membership memberShip) {
    return memberRepo.save(memberShip);
    }
    public Membership update(Long id, Membership memberShip) {
        Membership member =memberRepo.findById(id).orElseThrow();
        member.setStartDate(memberShip.getStartDate());
        member.setEndDate(memberShip.getEndDate());
        member.setStatus(memberShip.getStatus());
        member.setMaxLoanBooks(memberShip.getMaxLoanBooks());
        member.setMaxReservationBooks(memberShip.getMaxReservationBooks());
        return memberRepo.save(member);

    }

    public Membership delete(Long id) {
        Membership member = memberRepo.findById(id).orElseThrow();
        member.setStatus("INACTIVE");
        return memberRepo.save(member);
    }


}
