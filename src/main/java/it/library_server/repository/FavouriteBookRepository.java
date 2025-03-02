package it.library_server.repository;

import it.library_server.entity.FavouriteBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface FavouriteBookRepository extends JpaRepository<FavouriteBook, Long> {
    Optional<FavouriteBook> findByUserIdAndBookId(UUID userId, Long bookId);
    List<FavouriteBook> findByUserId(UUID userId);
}
