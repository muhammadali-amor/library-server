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

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(nullable = false)
    private Integer rating;

    @Column(nullable = false)
    private String text;
}

