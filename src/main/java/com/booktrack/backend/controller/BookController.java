package com.booktrack.backend.controller;

import com.booktrack.backend.dto.BookResponse;
import com.booktrack.backend.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class BookController {
    private final BookService bookService;

    @GetMapping
    public ResponseEntity<Page<BookResponse>> search(@RequestParam(required = false, defaultValue = "") String query,@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(bookService.searchBooks(query, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getDetails(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @GetMapping("/top10")
    public ResponseEntity<List<BookResponse>> getTop10() {
        return ResponseEntity.ok(bookService.getTop10Books());
    }
}