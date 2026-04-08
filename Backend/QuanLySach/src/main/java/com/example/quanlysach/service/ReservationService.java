package com.example.quanlysach.service;

import com.example.quanlysach.model.Reservation;
import com.example.quanlysach.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepo;

    public ReservationService(ReservationRepository reservationRepo) {
        this.reservationRepo = reservationRepo;
    }

    public List<Reservation> getAllReservations() {
        return reservationRepo.findAll();
    }

    public Reservation getReservationById(Long id) {
        return reservationRepo.findById(id).orElseThrow();
    }

    public Reservation add(Reservation reservation) {
        return reservationRepo.save(reservation);
    }

    public Reservation update(Long id, Reservation reservation) {
        Reservation r = reservationRepo.findById(id).orElseThrow();
        r.setUserId(reservation.getUserId());
        r.setBookId(reservation.getBookId());
        r.setReservationDate(reservation.getReservationDate());
        r.setExpiryDate(reservation.getExpiryDate());
        r.setStatus(reservation.getStatus());
        r.setPriority(reservation.getPriority());
        return reservationRepo.save(r);
    }

    public Reservation delete(Long id) {
        Reservation r = reservationRepo.findById(id).orElseThrow();
        r.setStatus("INACTIVE");
        return reservationRepo.save(r);
    }
}

