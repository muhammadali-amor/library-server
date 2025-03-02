package it.library_server.service;

import it.library_server.entity.Book;
import it.library_server.entity.Reviews;
import it.library_server.entity.User;
import it.library_server.exception.ResourceNotFoundException;
import it.library_server.implement.service.ReviewsServiceImpl;
import it.library_server.payload.ApiResponse;
import it.library_server.payload.req.ReqReviewsDto;
import it.library_server.payload.res.ResReviewsDto;
import it.library_server.repository.AuthRepository;
import it.library_server.repository.BookRepository;
import it.library_server.repository.ReviewsRepository;
import it.library_server.utils.Messages;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ReviewsService implements ReviewsServiceImpl {
    private static final Logger logger = LoggerFactory.getLogger(ReviewsService.class);
    private final BookRepository bookRepository;
    private final AuthRepository authRepository;
    private final ReviewsRepository reviewsRepository;


    @Override
    public List<ResReviewsDto> getAllComment(Long id) {
        if (!existBook(id)) {
            throw new ResourceNotFoundException(404, "getBook", "BookId", id);
        }
        return reviewsRepository.findAllByBookId(id).stream()
                .map(this::mapToResReviews)
                .toList();
    }

    @Override
    public ApiResponse<?> sendComment(UUID userId, Long bookId, ReqReviewsDto reviewsDto) {
        try {
            User user = authRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(404, "getUser", "userId", userId));
            Book book = bookRepository.findById(bookId).orElseThrow(() -> new ResourceNotFoundException(404, "getBook", "bookId", bookId));
            //user bu kitobga sharx yozgan yoki yoqligini aniqlash uchun
            if (reviewsRepository.existByUserIdAndBookId(userId, bookId)) {
                logger.error(Messages.SEND_NOT_COMMENT);
                return new ApiResponse<>(Messages.SEND_NOT_COMMENT, false);
            }
            //yangi sharx yaratish agar foydalanuvchi tanlagan kitobgiga sharx yozmagan bolsa
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

    @Override
    public double getColculatorReting(Long bookId) {
        if (!existBook(bookId)) {
            throw new ResourceNotFoundException(404, "getBook", "BookId", bookId);
        }
        List<Reviews> reviews = reviewsRepository.findAllByBookId(bookId);
        // Agar sharhlar bo‘lmasa, 0 qaytadi
        if (reviews.isEmpty()) {
            return 0.0;
        }
        double averageRating = reviews.stream()
                .mapToInt(Reviews::getRating)  // Reytinglarni int ga o‘tkazamiz
                .average()                     // O‘rtachasini hisoblaymiz
                .orElse(0.0);                  // Agar sharh bo‘lmasa, 0 qaytaramiz
        return averageRating;
    }

    private boolean existBook(Long id) {
        boolean exists = bookRepository.existsById(id);
        if (!exists) {
            logger.warn(Messages.WARNING_BOOK);
        }
        return exists;
    }

    private ResReviewsDto mapToResReviews(Reviews reviews) {
        return new ResReviewsDto(
                reviews.getText(),
                reviews.getRating(),
                reviews.getUser().getName(),
                reviews.getUser().getSurname(),
                reviews.getUser().getPhotoId(),
                reviews.getCreatedAt()
        );
    }
}
