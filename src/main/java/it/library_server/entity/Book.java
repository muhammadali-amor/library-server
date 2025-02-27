package it.library_server.entity;


import it.library_server.entity.enums.Age;
import it.library_server.entity.enums.Genre;
import it.library_server.entity.templates.AbsNameEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book extends AbsNameEntity {
    @Column(nullable = false)
    private String author;//kitob kim tomonidan yozilgan

    @Column(nullable = false)
    private int publisherYear;//kitob yili

    @Column(nullable = false)
    private String language;//kitob tili

    @Enumerated(EnumType.STRING)
    private Age age;//kitob yosh chegarasi

    @Column(nullable = false)
    private String coverImage;//kitob janri

    @Column(nullable = false)
    private String description;//kitob haqida

    @Column(nullable = false)
    private String bookPdfName;//kitob pdf

    @ElementCollection
    @CollectionTable(name = "movie_genres", joinColumns = @JoinColumn(name = "movie_id"))
    @Enumerated(EnumType.STRING)
    private List<Genre> genres; //kitob janri
}
