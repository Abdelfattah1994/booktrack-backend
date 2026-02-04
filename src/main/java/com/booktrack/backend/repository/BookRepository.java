package com.booktrack.backend.repository;

import com.booktrack.backend.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    // Recherche par titre ou auteur avec pagination
    Page<Book> findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(String title, String author, Pageable pageable);

    List<Book> findTop10ByOrderByViewCountDesc();
}
