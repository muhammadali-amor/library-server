package it.library_server.implement.service;

import it.library_server.entity.Reviews;
import it.library_server.payload.ApiResponse;
import it.library_server.payload.ReqReviews;
import it.library_server.payload.res.ResReviews;

import java.util.List;
import java.util.UUID;

public interface ReviewsServiceImpl {
    List<ResReviews> getAllComment(Long id);

    ApiResponse<?> sendComment(UUID userId, Long bookId, ReqReviews reviewsDto);

    double getColculatorReting(Long bookId);
}
