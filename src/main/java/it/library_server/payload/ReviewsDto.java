package it.library_server.payload;

import jakarta.persistence.Column;

import java.util.UUID;

public record ReviewsDto(
        Integer rating,
        String text
) {
}
