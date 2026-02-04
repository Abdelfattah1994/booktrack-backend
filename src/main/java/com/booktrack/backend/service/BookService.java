package com.booktrack.backend.service;

import com.booktrack.backend.dto.BookResponse;
import com.booktrack.backend.entity.Book;
import com.booktrack.backend.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public Page<BookResponse> searchBooks(String query, Pageable pageable) {
        return bookRepository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(query, query, pageable)
                .map(this::convertToDTO);
    }

    private BookResponse convertToDTO(Book book) {
        return new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getSummary(),
                book.getRating()
        );
    }

    public BookResponse getBookById(Long id) {
        return bookRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }
}