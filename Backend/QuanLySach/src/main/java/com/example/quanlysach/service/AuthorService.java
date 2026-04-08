package com.example.quanlysach.service;

import com.example.quanlysach.model.Author;
import com.example.quanlysach.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    private final AuthorRepository authorRepo;

    public AuthorService(AuthorRepository authorRepo) {
        this.authorRepo = authorRepo;
    }

    public List<Author> getAllAuthors() {
        return authorRepo.findAll();
    }

    public Author getAuthorById(Long id) {
        return authorRepo.findById(id).orElseThrow();
    }

    public Author add(Author author) {
        return authorRepo.save(author);
    }

    public Author update(Long id, Author author) {
        Author a = authorRepo.findById(id).orElseThrow();
        a.setName(author.getName());
        a.setActiveYears(author.getActiveYears());
        a.setAwardsCount(author.getAwardsCount());
        return authorRepo.save(a);
    }

    public Author delete(Long id) {
        Author a = authorRepo.findById(id).orElseThrow();
        a.setStatus("INACTIVE");
        return authorRepo.save(a);
    }
}

