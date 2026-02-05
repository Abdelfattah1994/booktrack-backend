package com.booktrack.backend.controller;

import com.booktrack.backend.dto.BookResponse;
import com.booktrack.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Set;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {
    private final UserService userService;

    @PostMapping("/favorites/{bookId}")
    public ResponseEntity<Void> addFavorite(@PathVariable Long bookId, Principal principal) {
        userService.addFavorite(principal.getName(), bookId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/favorites")
    public ResponseEntity<Set<BookResponse>> getFavorites(Principal principal) {
        return ResponseEntity.ok(userService.getUserFavorites(principal.getName()));
    }
}
