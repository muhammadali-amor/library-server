package it.library_server.repository;

import it.library_server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AuthRepository extends JpaRepository<User, UUID> {

    Optional<User> findUserByEmail(String email);

    Optional<User> findByEmail(String email);

    boolean existsUserByEmail(String email);
}
