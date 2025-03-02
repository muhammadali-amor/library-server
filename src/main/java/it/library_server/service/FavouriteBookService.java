package it.library_server.service;

import it.library_server.entity.Book;
import it.library_server.entity.FavouriteBook;
import it.library_server.entity.User;
import it.library_server.exception.ResourceNotFoundException;
import it.library_server.payload.ApiResponse;
import it.library_server.payload.BookDto;
import it.library_server.repository.AuthRepository;
import it.library_server.repository.BookRepository;
import it.library_server.repository.FavouriteBookRepository;
import it.library_server.utils.Messages;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavouriteBookService {
    private static final Logger logger = LoggerFactory.getLogger(FavouriteBookService.class);

    private final FavouriteBookRepository favouriteBookRepository;
    private final AuthRepository authRepository;
    private final BookRepository bookRepository;

    public List<Book> getFavouriteBooks(UUID userId) {
        logger.info("User ID: {}", userId);
        List<FavouriteBook> favouriteBooks = favouriteBookRepository.findByUserId(userId);
        logger.info("Favourite Books: {}", favouriteBooks);
        User user = authRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(404, "getUser", "user id", userId));
        return favouriteBookRepository.findByUserId(userId)
                .stream()
                .map(FavouriteBook::getBook)
                .collect(Collectors.toList());
    }

    @Transactional
    public ApiResponse<?> toggleFavourite(UUID userId, Long bookId) {
        try {
            Optional<FavouriteBook> existingFavourite = favouriteBookRepository.findByUserIdAndBookId(userId, bookId);
            if (existingFavourite.isPresent()) {
                favouriteBookRepository.delete(existingFavourite.get());
                logger.info(Messages.NOT_FAVOURITE_BOOK);
                return new ApiResponse<>(Messages.NOT_FAVOURITE_BOOK, true);
            }
            User user = authRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(404, "getUser", "user id", userId));
            Book book = bookRepository.findById(bookId).orElseThrow(() -> new ResourceNotFoundException(404, "getBook", "book id", bookId));
            FavouriteBook favouriteBook = FavouriteBook.builder()
                    .book(book)
                    .user(user)
                    .build();
            favouriteBookRepository.save(favouriteBook);
            logger.info(Messages.FAVOURITE_BOOK);
            return new ApiResponse<>(Messages.FAVOURITE_BOOK, true);
        } catch (Exception e) {
            logger.error(Messages.ERROR_OBJ, e.getMessage(), e);
            return new ApiResponse<>(Messages.ERROR, false);
        }
    }
}
