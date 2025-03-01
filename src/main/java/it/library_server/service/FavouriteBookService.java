package it.library_server.service;

import it.library_server.entity.FavouriteBook;
import it.library_server.payload.ApiResponse;
import it.library_server.repository.FavouriteBookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class FavouriteBookService {
    private final FavouriteBookRepository favouriteBookRepository;

    public ApiResponse<?> toggleFavourite(UUID userId, Long bookId) {
        Optional<FavouriteBook> existingFavourite = favouriteBookRepository.findByUserIdAndBookId(userId, bookId);

        if (existingFavourite.isPresent()) {
            FavouriteBook favouriteBook = existingFavourite.get();
            favouriteBookRepository.delete(favouriteBook);
        }
        return null;


    }
}
