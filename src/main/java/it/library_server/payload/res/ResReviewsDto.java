package it.library_server.payload.res;

import java.sql.Timestamp;

public record ResReviewsDto(
        String text,
        Integer rating,
        String userName,
        String userSurname,
        String userPhoto,
        Timestamp createdAt
) {
}
