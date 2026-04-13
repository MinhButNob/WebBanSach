package com.example.quanlysach.service;

import com.example.quanlysach.dto.BookDTO;
import com.example.quanlysach.model.Book;
import com.example.quanlysach.model.BookCategory;
import com.example.quanlysach.model.Category;
import com.example.quanlysach.model.Publisher;
import com.example.quanlysach.repository.BookCategoryRepository;
import com.example.quanlysach.repository.BookRepository;
import com.example.quanlysach.repository.CategoryRepository;
import com.example.quanlysach.repository.PublisherRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final BookRepository bookRepo;
    private final BookCategoryRepository bookCategoryRepo;
    private final CategoryRepository categoryRepo;
    private final PublisherRepository publisherRepo;

    public BookService(BookRepository bookRepo,
                       BookCategoryRepository bookCategoryRepo,
                       CategoryRepository categoryRepo,
                       PublisherRepository publisherRepo) {
        this.bookRepo = bookRepo;
        this.bookCategoryRepo = bookCategoryRepo;
        this.categoryRepo = categoryRepo;
        this.publisherRepo = publisherRepo;
    }

    // Lấy tất cả sách kèm thể loại
    public List<BookDTO> getAllBooks() {
        return bookRepo.findAll().stream()
                .map(this::toBookDTO)
                .collect(Collectors.toList());
    }

    // Lấy sách theo ID kèm thể loại
    public BookDTO getBookById(Long id) {
        Book book = bookRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sách với id: " + id));
        return toBookDTO(book);
    }

    // Tạo sách mới
    @Transactional
    public BookDTO createBook(BookDTO bookDTO) {
        Book book = toEntity(bookDTO);
        book.setStatus("ACTIVE");
        if (book.getTotalCopies() == null) book.setTotalCopies(0);
        if (book.getAvailableCopies() == null) book.setAvailableCopies(0);

        Book saved = bookRepo.save(book);

        // Thêm categories cho sách
        if (bookDTO.getCategories() != null && !bookDTO.getCategories().isEmpty()) {
            for (String categoryName : bookDTO.getCategories()) {
                addCategoryToBook(saved.getId(), categoryName);
            }
        }

        return toBookDTO(saved);
    }

    // Cập nhật sách
    @Transactional
    public BookDTO updateBook(Long id, BookDTO bookDTO) {
        Book book = bookRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sách với id: " + id));

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

        if (bookDTO.getPublisherId() != null) {
            Publisher publisher = publisherRepo.findById(bookDTO.getPublisherId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy nhà xuất bản"));
            book.setPublisherId(bookDTO.getPublisherId());
            book.setPublisher(publisher);
        }

        Book updated = bookRepo.save(book);

        // Cập nhật categories: xóa cũ, thêm mới
        bookCategoryRepo.deleteByBook_Id(id);
        if (bookDTO.getCategories() != null && !bookDTO.getCategories().isEmpty()) {
            for (String categoryName : bookDTO.getCategories()) {
                addCategoryToBook(id, categoryName);
            }
        }

        return toBookDTO(updated);
    }

    // Xóa mềm sách
    @Transactional
    public void deleteBookById(Long id) {
        Book book = bookRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sách với id: " + id));
        book.setStatus("INACTIVE");
        bookRepo.save(book);
    }

    public List<BookDTO> getBooksByPublisher(Long publisherId) {
        return bookRepo.findByPublisherId(publisherId).stream()
                .map(this::toBookDTO)
                .collect(Collectors.toList());
    }

    public List<BookDTO> searchBooks(String keyword) {
        return bookRepo.findByTitleContainingIgnoreCase(keyword).stream()
                .map(this::toBookDTO)
                .collect(Collectors.toList());
    }

    public List<BookDTO> getActiveBooks() {
        return bookRepo.findByStatus("ACTIVE").stream()
                .map(this::toBookDTO)
                .collect(Collectors.toList());
    }

    // Thêm thể loại cho sách
    @Transactional
    public void addCategoryToBook(Long bookId, String categoryName) {
        Book book = bookRepo.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sách"));

        Category category = categoryRepo.findByName(categoryName)
                .orElseGet(() -> {
                    Category newCat = new Category();
                    newCat.setName(categoryName);
                    newCat.setStatus("ACTIVE");
                    return categoryRepo.save(newCat);
                });

        // Kiểm tra xem đã tồn tại chưa - Sửa method đúng
        List<BookCategory> existing = bookCategoryRepo.findByBook_IdAndStatus(bookId, "ACTIVE");
        boolean exists = existing.stream()
                .anyMatch(bc -> bc.getCategory().getId().equals(category.getId()));

        if (!exists) {
            BookCategory bookCategory = new BookCategory();
            bookCategory.setBook(book);
            bookCategory.setCategory(category);
            bookCategory.setStatus("ACTIVE");
            bookCategoryRepo.save(bookCategory);
        }
    }

    // Lấy sách theo thể loại
    public List<BookDTO> getBooksByCategory(String categoryName) {
        return bookRepo.findBooksByCategoryName(categoryName).stream()
                .map(this::toBookDTO)
                .collect(Collectors.toList());
    }

    // Chuyển Entity -> DTO
    private BookDTO toBookDTO(Book book) {
        // Lấy danh sách tên thể loại
        List<String> categories = bookCategoryRepo.findCategoryNamesByBookId(book.getId());

        return BookDTO.builder()
                .id(book.getId())
                .title(book.getTitle())
                .isbn(book.getIsbn())
                .language(book.getLanguage())
                .edition(book.getEdition())
                .totalCopies(book.getTotalCopies())
                .availableCopies(book.getAvailableCopies())
                .minLoanDays(book.getMinLoanDays())
                .maxLoanDays(book.getMaxLoanDays())
                .popularityScore(book.getPopularityScore())
                .publisherId(book.getPublisherId())
                .status(book.getStatus())
                .publisherName(book.getPublisher() != null ? book.getPublisher().getName() : null)
                .categories(categories)
                .build();
    }

    // Chuyển DTO -> Entity
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

        if (bookDTO.getPublisherId() != null) {
            book.setPublisherId(bookDTO.getPublisherId());
        }

        return book;
    }
}

