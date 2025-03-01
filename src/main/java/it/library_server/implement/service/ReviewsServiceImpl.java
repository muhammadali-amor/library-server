package it.library_server.implement.service;

import it.library_server.payload.ApiResponse;
import it.library_server.payload.ReviewsDto;

import java.util.UUID;

public interface ReviewsServiceImpl {
    ApiResponse<?> sendComment(UUID userId, Long bookId, ReviewsDto reviewsDto);
}
