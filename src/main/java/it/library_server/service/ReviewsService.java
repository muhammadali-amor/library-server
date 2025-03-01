package it.library_server.service;

import it.library_server.entity.Book;
import it.library_server.entity.Reviews;
import it.library_server.entity.User;
import it.library_server.exception.ResourceNotFoundException;
import it.library_server.payload.ApiResponse;
import it.library_server.payload.ReviewsDto;
import it.library_server.repository.AuthRepository;
import it.library_server.repository.BookRepository;
import it.library_server.repository.ReviewsRepository;
import it.library_server.utils.Messages;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.net.Authenticator;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewsService {
    private static final Logger logger = LoggerFactory.getLogger(ReviewsService.class);
    private final BookRepository bookRepository;
    private final AuthRepository authRepository;
    private final ReviewsRepository reviewsRepository;

    public ApiResponse<?> sendReviews(UUID userId, Long bookId, ReviewsDto reviewsDto) {
        try {
            User user = authRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(404, "getUser", "userId", userId));
            Book book = bookRepository.findById(bookId).orElseThrow(() -> new ResourceNotFoundException(404, "getBook", "bookId", bookId));
            Optional<Reviews> byUserIdAndBookId = reviewsRepository.findByUserIdAndBookId(userId, bookId);
            if (byUserIdAndBookId.isPresent()) {
                logger.error(Messages.SEND_NOT_COMMENT);
                return new ApiResponse<>(Messages.SEND_NOT_COMMENT, false);
            }
            Reviews reviews = Reviews.builder()
                    .user(user)
                    .book(book)
                    .text(reviewsDto.text())
                    .rating(reviewsDto.rating())
                    .build();
            reviewsRepository.save(reviews);
            logger.info(Messages.SEND_COMMENT);
            return new ApiResponse<>(Messages.SEND_COMMENT, true);
        } catch (Exception e) {
            logger.error(Messages.ERROR_COMMENT);
            return new ApiResponse<>(Messages.ERROR, false);
        }
    }
}
