package com.booktrack.backend.service;

import com.booktrack.backend.dto.BookResponse;
import com.booktrack.backend.entity.Book;
import com.booktrack.backend.entity.User;
import com.booktrack.backend.repository.BookRepository;
import com.booktrack.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    @Transactional
    public void addFavorite(String username, Long bookId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        user.getFavoriteBooks().add(book);
        userRepository.save(user);
    }

    public Set<BookResponse> getUserFavorites(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return user.getFavoriteBooks().stream()
                .map(book -> new BookResponse(
                        book.getId(),
                        book.getTitle(),
                        book.getAuthor(),
                        book.getSummary(),
                        book.getRating()))
                .collect(Collectors.toSet());
    }
}
