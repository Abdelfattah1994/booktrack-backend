package com.booktrack.backend.service;

import com.booktrack.backend.dto.BookResponse;
import com.booktrack.backend.entity.Book;
import com.booktrack.backend.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    private Book sampleBook;

    @BeforeEach
    void setUp() {
        sampleBook = new Book();
        sampleBook.setId(1L);
        sampleBook.setTitle("The Little Prince");
        sampleBook.setAuthor("Antoine de Saint-Exupéry");
        sampleBook.setViewCount(10);
        sampleBook.setRating(5.0);
    }

    @Test
    @DisplayName("Should increment view count and return book DTO when ID exists")
    void getBookById_Success() {

        when(bookRepository.findById(1L)).thenReturn(Optional.of(sampleBook));

        BookResponse result = bookService.getBookById(1L);

        assertThat(result.title()).isEqualTo("The Little Prince");
        assertThat(sampleBook.getViewCount()).isEqualTo(11);
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should throw RuntimeException when book ID is not found")
    void getBookById_NotFound() {

        when(bookRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookService.getBookById(99L))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Book not found");
    }

    @Test
    @DisplayName("Should return a paginated list of books matching the search query")
    void searchBooks_ShouldReturnPaginatedResults() {

        String query = "Prince";
        Pageable pageable = PageRequest.of(0, 10);
        Page<Book> bookPage = new PageImpl<>(List.of(sampleBook));

        when(bookRepository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(query, query, pageable))
                .thenReturn(bookPage);

        Page<BookResponse> result = bookService.searchBooks(query, pageable);

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).title()).isEqualTo("The Little Prince");
    }

    @Test
    @DisplayName("Should return the top 10 most viewed books")
    void getTop10Books_ShouldReturnList() {

        when(bookRepository.findTop10ByOrderByViewCountDesc()).thenReturn(List.of(sampleBook));

        List<BookResponse> result = bookService.getTop10Books();

        assertThat(result).hasSize(1);
        verify(bookRepository, times(1)).findTop10ByOrderByViewCountDesc();
    }
}