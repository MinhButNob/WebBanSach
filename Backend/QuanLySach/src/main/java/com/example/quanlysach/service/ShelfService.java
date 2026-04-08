package com.example.quanlysach.service;

import com.example.quanlysach.model.Shelf;
import com.example.quanlysach.repository.ShelfRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShelfService {
    private final ShelfRepository shelfRepo;

    public ShelfService(ShelfRepository shelfRepo) {
        this.shelfRepo = shelfRepo;
    }

    public List<Shelf> getAllShelves() {
        return shelfRepo.findAll();
    }

    public Shelf getShelfById(Long id) {
        return shelfRepo.findById(id).orElseThrow();
    }

    public Shelf add(Shelf shelf) {
        return shelfRepo.save(shelf);
    }

    public Shelf update(Long id, Shelf shelf) {
        Shelf s = shelfRepo.findById(id).orElseThrow();
        s.setName(shelf.getName());
        s.setLocation(shelf.getLocation());
        return shelfRepo.save(s);
    }

    public Shelf delete(Long id) {
        Shelf s = shelfRepo.findById(id).orElseThrow();
        s.setStatus("INACTIVE");
        return shelfRepo.save(s);
    }
}

