package it.library_server.implement.controller;

import it.library_server.payload.req.ReqReviewsDto;
import org.springframework.http.HttpEntity;

import java.util.UUID;

public interface ReviewsControllerImpl {
    HttpEntity<?> getAllComment(Long id);

    HttpEntity<?> getAllReting(Long id);

    HttpEntity<?> sendComment(UUID userId, Long bookId, ReqReviewsDto reviewsDto);
}
