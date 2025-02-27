package it.library_server.entity;


import it.library_server.entity.enums.Age;
import it.library_server.entity.templates.AbsNameEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Books extends AbsNameEntity {
    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private int publisher_year;

    @Column(nullable = false)
    private String language;

    private Age age;

    @Column(nullable = false)
    private String coverImage;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String bookPdfName;
}
