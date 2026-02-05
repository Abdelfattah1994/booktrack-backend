package com.booktrack.backend.service;

import com.booktrack.backend.dto.BookResponse;
import com.booktrack.backend.entity.Book;
import com.booktrack.backend.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public Page<BookResponse> searchBooks(String query, Pageable pageable) {
        return bookRepository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(query, query, pageable)
                .map(this::convertToDTO);
    }

    @Transactional
    public BookResponse getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        book.setViewCount(book.getViewCount() + 1);

        return convertToDTO(book);
    }

    public List<BookResponse> getTop10Books() {
        return bookRepository.findTop10ByOrderByViewCountDesc().stream()
                .map(this::convertToDTO)
                .toList();
    }

    private BookResponse convertToDTO(Book book) {
        return new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getSummary(),
                book.getRating(),
                book.getIsbn()
        );
    }
}