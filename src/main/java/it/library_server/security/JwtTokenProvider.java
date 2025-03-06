package it.library_server.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import it.library_server.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class JwtTokenProvider {

    @Value("${app.jwtSecretKey}")
    private String JWT_SECRET;

    @Value("${app.jwtExpirationHours}") // 2 soat default
    private int JWT_EXPIRATION_HOURS;

    public String generateAccessToken(User user) {
        try {
            if (JWT_SECRET == null || JWT_SECRET.isEmpty()) {
                throw new IllegalStateException("JWT_SECRET konfiguratsiya qilinmagan!");
            }

            Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET);
            return JWT.create()
                    .withSubject(user.getEmail())
                    .withClaim("email", user.getEmail())
                    .withClaim("provider", user.getAuthProvider()) // OAuth yoki oddiy login
                    .withExpiresAt(genAccessExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error while generating token", exception);
        }
    }

    public String validateToken(String token) {
        try {
            if (JWT_SECRET == null || JWT_SECRET.isEmpty()) {
                throw new IllegalStateException("JWT_SECRET konfiguratsiya qilinmagan!");
            }

            Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET);
            return JWT.require(algorithm)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            return null; // Xatolik bo‘lsa, null qaytariladi
        }
    }

    private Instant genAccessExpirationDate() {
        return LocalDateTime.now().plusHours(JWT_EXPIRATION_HOURS).toInstant(ZoneOffset.UTC);
    }
}
