package com.example.quanlysach.service;

import com.example.quanlysach.dto.BookDTO;
import com.example.quanlysach.model.Book;
import com.example.quanlysach.model.Publisher;
import com.example.quanlysach.repository.BookRepository;
import com.example.quanlysach.repository.PublisherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepo;
    private final PublisherRepository publisherRepo;
    public BookService(BookRepository bookRepo, PublisherRepository publisherRepo) {
        this.bookRepo = bookRepo;
        this.publisherRepo = publisherRepo;
    }

    public List<BookDTO> getAllBooks() {
       return bookRepo.findAll().stream().map(this::toBookDTO).toList();
    }

    public BookDTO getBookById(Long id) {
        return bookRepo.findById(id).map(this::toBookDTO).orElseThrow();
    }

    public BookDTO createBook(BookDTO bookDTO) {
        Book b = toEntity(bookDTO);
        b.setStatus("ACTIVE");
        return toBookDTO(bookRepo.save(b));
    }
    public void deleteBookById(Long id) {
        Book b = bookRepo.findById(id).orElseThrow();
        b.setStatus("INACTIVE");
        bookRepo.save(b);
    }

    public BookDTO updateBook(Long id, BookDTO bookDTO) {
        Book b = bookRepo.findById(id).orElseThrow();
        b.setTitle(bookDTO.getTitle());
        b.setIsbn(bookDTO.getIsbn());
        b.setLanguage(bookDTO.getLanguage());
        b.setEdition(bookDTO.getEdition());
        b.setTotalCopies(bookDTO.getTotalCopies());
        b.setAvailableCopies(bookDTO.getAvailableCopies());
        b.setMinLoanDays(bookDTO.getMinLoanDays());
        b.setMaxLoanDays(bookDTO.getMaxLoanDays());
        b.setPopularityScore(bookDTO.getPopularityScore());
        b.setStatus(bookDTO.getStatus());
        applyPublisher(b, bookDTO.getPublisherId());
        return toBookDTO(bookRepo.save(b));
    }

    private BookDTO toBookDTO(Book book) {
        return new BookDTO(
                book.getId(),
                book.getTitle(),
                book.getIsbn(),
                book.getLanguage(),
                book.getEdition(),
                book.getTotalCopies(),
                book.getAvailableCopies(),
                book.getMinLoanDays(),
                book.getMaxLoanDays(),
                book.getPopularityScore(),
                book.getStatus(),
                book.getPublisherId(),
                book.getPublisher() != null ? book.getPublisher().getName() : null

        );
    }

    private Book toEntity(BookDTO bookDTO) {
        Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setIsbn(bookDTO.getIsbn());
        book.setLanguage(bookDTO.getLanguage());
        book.setEdition(bookDTO.getEdition());
        book.setTotalCopies(bookDTO.getTotalCopies());
        book.setAvailableCopies(bookDTO.getAvailableCopies());
        book.setMinLoanDays(bookDTO.getMinLoanDays());
        book.setMaxLoanDays(bookDTO.getMaxLoanDays());
        book.setPopularityScore(bookDTO.getPopularityScore());
        book.setStatus(bookDTO.getStatus());

        applyPublisher(book, bookDTO.getPublisherId());
        return book;
    }

    private void applyPublisher(Book book, Long publisherId) {
        book.setPublisherId(publisherId);

        if (publisherId == null) {
            book.setPublisher(null);
            return;
        }

        Publisher publisher = publisherRepo.findById(publisherId)
                .orElseThrow(() -> new RuntimeException("Publisher not found with id: " + publisherId));
        book.setPublisher(publisher);
    }

}

