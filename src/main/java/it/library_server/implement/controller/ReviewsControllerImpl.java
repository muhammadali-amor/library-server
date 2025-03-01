package it.library_server.implement.controller;

import it.library_server.payload.ReviewsDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;

import java.util.UUID;

public interface ReviewsControllerImpl {
    HttpEntity<?> sendComment(UUID userId, Long bookId, ReviewsDto reviewsDto);
}
