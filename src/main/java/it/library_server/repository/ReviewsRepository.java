package it.library_server.repository;

import it.library_server.entity.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ReviewsRepository extends JpaRepository<Reviews, Long> {

    Optional<Reviews> findByUserIdAndBookId(UUID userId, Long bookId);//foydalanuvchi kitobga sharx yozgan yoki yuqligi aniqlash uchun

}
