package it.library_server.repository;

import it.library_server.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("SELECT COUNT(b) > 0 FROM Book b WHERE LOWER(b.name) = LOWER(:name)")
    boolean existsByNameIgnoreCase(@Param("title") String name);

    boolean existsById(Long id);
}
