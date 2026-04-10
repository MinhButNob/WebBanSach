package com.example.quanlysach.controller;

import com.example.quanlysach.model.Publisher;
import com.example.quanlysach.service.PublisherService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/publisher")
public class PublisherController {
    private final PublisherService publisherService;
    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @GetMapping
    public List<Publisher> getAllPublisher() {
        return publisherService.getAllPublishers();
    }

    @PostMapping
    public Publisher createPublisher(@RequestBody Publisher publisher) {
        return publisherService.add(publisher);
    }

    @PutMapping("/{id}")
    public Publisher updatePublisher(@PathVariable Long id, @RequestBody Publisher publisher) {

        return publisherService.update(id, publisher);
    }

    @DeleteMapping("/{id}")
    public Publisher deletePublisher(@PathVariable Long id) {
        return publisherService.delete(id);
    }
}
