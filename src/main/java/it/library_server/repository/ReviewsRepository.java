package it.library_server.repository;

import it.library_server.entity.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ReviewsRepository extends JpaRepository<Reviews, Long> {

    List<Reviews> findAllByBookId(Long bookId); // Aynan bitta kitob boyich komentlar royxatini olish uchun

    boolean existByUserIdAndBookId(UUID userId, Long bookId);//foydalanuvchi bu kitobga sharx yozgan yoki yoqligini aniqlash uchun


}
