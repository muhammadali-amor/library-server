package it.library_server.entity;

import it.library_server.entity.templates.AbsNameEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.relational.core.sql.In;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Reviews{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private Long bookId;

    @Column(nullable = false)
    private Integer rating;

    @Column(nullable = false)
    private String comment;
}

