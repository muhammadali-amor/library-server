package it.library_server.implement.controller;

import it.library_server.payload.ReqReviews;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;

import java.util.UUID;

public interface ReviewsControllerImpl {
    HttpEntity<?> sendComment(UUID userId, Long bookId, ReqReviews reviewsDto);
}
