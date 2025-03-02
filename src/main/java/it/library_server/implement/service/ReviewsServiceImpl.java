package it.library_server.implement.service;

import it.library_server.payload.ApiResponse;
import it.library_server.payload.req.ReqReviewsDto;
import it.library_server.payload.res.ResReviewsDto;

import java.util.List;
import java.util.UUID;

public interface ReviewsServiceImpl {
    List<ResReviewsDto> getAllComment(Long id);

    ApiResponse<?> sendComment(UUID userId, Long bookId, ReqReviewsDto reviewsDto);

    double getCalculatedRating(Long bookId);
}
