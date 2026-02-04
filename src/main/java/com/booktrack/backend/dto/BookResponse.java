package com.booktrack.backend.dto;

public record BookResponse(
        Long id,
        String title,
        String author,
        String summary,
        Double rating
) {}
