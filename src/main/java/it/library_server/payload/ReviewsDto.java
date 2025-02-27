package it.library_server.payload;

import jakarta.persistence.Column;

import java.util.UUID;

public record ReviewsDto (
        Long id,
        UUID userId,
        Long bookId,
        Integer rating,
        String comment
){ }
