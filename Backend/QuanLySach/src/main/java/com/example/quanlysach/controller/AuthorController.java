package com.example.quanlysach.controller;

import com.example.quanlysach.model.Author;
import com.example.quanlysach.service.AuthorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
@CrossOrigin("*")
public class AuthorController {
    private final AuthorService service;

    public AuthorController(AuthorService service) {
        this.service = service;
    }

    @GetMapping
    public List<Author> getAllAuthors() {
        return service.getAllAuthors();
    }

    @GetMapping("/{id}")
    public Author getAuthorById(@PathVariable Long id) {
        return service.getAuthorById(id);
    }

    @PostMapping
    public Author addAuthor(@RequestBody Author author) {
        return service.add(author);
    }

    @PostMapping("/{id}")
    public Author updateAuthor(@PathVariable Long id, @RequestBody Author author) {
        return service.update(id, author);
    }

    @DeleteMapping("/{id}")
    public Author deleteAuthor(@PathVariable Long id) {
        return service.delete(id);
    }
}

