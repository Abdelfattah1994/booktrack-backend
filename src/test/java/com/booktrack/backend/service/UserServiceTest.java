package com.booktrack.backend.service;

import com.booktrack.backend.dto.BookResponse;
import com.booktrack.backend.entity.Book;
import com.booktrack.backend.entity.User;
import com.booktrack.backend.repository.BookRepository;
import com.booktrack.backend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private UserService userService;

    private User sampleUser;
    private Book sampleBook;

    @BeforeEach
    void setUp() {
        sampleUser = new User();
        sampleUser.setUsername("abdel");
        sampleUser.setFavoriteBooks(new HashSet<>());

        sampleBook = new Book();
        sampleBook.setId(1L);
        sampleBook.setTitle("1984");
        sampleBook.setAuthor("George Orwell");
    }

    @Test
    @DisplayName("Should successfully add a book to user favorites")
    void addFavorite_Success() {

        when(userRepository.findByUsername("abdel")).thenReturn(Optional.of(sampleUser));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(sampleBook));

        userService.addFavorite("abdel", 1L);

        assertThat(sampleUser.getFavoriteBooks()).contains(sampleBook);
        verify(userRepository, times(1)).save(sampleUser);
    }

    @Test
    @DisplayName("Should throw exception when user is not found during addFavorite")
    void addFavorite_UserNotFound() {

        when(userRepository.findByUsername("unknown")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.addFavorite("unknown", 1L))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("User not found");
    }

    @Test
    @DisplayName("Should successfully return user favorites as DTO set")
    void getUserFavorites_Success() {

        sampleUser.getFavoriteBooks().add(sampleBook);
        when(userRepository.findByUsername("abdel")).thenReturn(Optional.of(sampleUser));

        Set<BookResponse> favorites = userService.getUserFavorites("abdel");

        assertThat(favorites).hasSize(1);
        assertThat(favorites.iterator().next().title()).isEqualTo("1984");
    }
}