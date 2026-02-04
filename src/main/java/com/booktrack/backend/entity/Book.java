package com.booktrack.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
    @Column(length = 1000)
    private String summary;
    private Double rating;
    private Integer viewCount = 0;
}