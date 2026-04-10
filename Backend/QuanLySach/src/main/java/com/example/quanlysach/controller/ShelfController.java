package com.example.quanlysach.controller;

import com.example.quanlysach.model.Shelf;
import com.example.quanlysach.service.ShelfService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shelves")
@CrossOrigin("*")
public class ShelfController {
    private final ShelfService service;

    public ShelfController(ShelfService service) {
        this.service = service;
    }

    @GetMapping
    public List<Shelf> getAllShelves() {
        return service.getAllShelves();
    }

    @GetMapping("/{id}")
    public Shelf getShelfById(@PathVariable Long id) {
        return service.getShelfById(id);
    }

    @PostMapping
    public Shelf addShelf(@RequestBody Shelf shelf) {
        return service.add(shelf);
    }

    @PutMapping("/{id}")
    public Shelf updateShelf(@PathVariable Long id, @RequestBody Shelf shelf) {
        return service.update(id, shelf);
    }

    @DeleteMapping("/{id}")
    public Shelf deleteShelf(@PathVariable Long id) {
        return service.delete(id);
    }
}

