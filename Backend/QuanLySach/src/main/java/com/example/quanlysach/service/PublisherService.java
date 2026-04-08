package com.example.quanlysach.service;

import com.example.quanlysach.model.Publisher;
import com.example.quanlysach.repository.PublisherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublisherService {
    private final PublisherRepository publisherRepo;

    public PublisherService(PublisherRepository publisherRepo) {
        this.publisherRepo = publisherRepo;
    }

    public List<Publisher> getAllPublishers() {
        return publisherRepo.findAll();
    }

    public Publisher getPublisherById(Long id) {
        return publisherRepo.findById(id).orElseThrow();
    }

    public Publisher add(Publisher publisher) {
        return publisherRepo.save(publisher);
    }

    public Publisher update(Long id, Publisher publisher) {
        Publisher p = publisherRepo.findById(id).orElseThrow();
        p.setName(publisher.getName());
        p.setTotalPublishedBooks(publisher.getTotalPublishedBooks());
        p.setAverageLoanDays(publisher.getAverageLoanDays());
        return publisherRepo.save(p);
    }

    public Publisher delete(Long id) {
        Publisher p = publisherRepo.findById(id).orElseThrow();
        p.setStatus("INACTIVE");
        return publisherRepo.save(p);
    }
}

