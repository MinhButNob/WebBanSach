package com.example.quanlysach.service;

import com.example.quanlysach.model.MemberShip;
import com.example.quanlysach.repository.MemberShipRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MembershipService {
    private final MemberShipRepository memberRepo;
    public MembershipService(MemberShipRepository memberRepo) {
        this.memberRepo = memberRepo;
    }

    public List<MemberShip> getAllMemberShip() {
        return memberRepo.findAll();
    }

    public MemberShip getMemberShipById(Long id) {
        return memberRepo.findById(id).orElseThrow();

    }

    public MemberShip add(MemberShip memberShip) {
    return memberRepo.save(memberShip);
    }
    public MemberShip update(Long id,MemberShip memberShip) {
        MemberShip member =memberRepo.findById(id).orElseThrow();
        member.setStartDate(memberShip.getStartDate());
        member.setEndDate(memberShip.getEndDate());
        member.setStatus(memberShip.getStatus());
        member.setMaxLoanBooks(memberShip.getMaxLoanBooks());
        member.setMaxReservationBooks(memberShip.getMaxReservationBooks());
        return memberRepo.save(member);

    }

    public MemberShip delete(Long id) {
        MemberShip member = memberRepo.findById(id).orElseThrow();
        member.setStatus("INACTIVE");
        return memberRepo.save(member);
    }


}
