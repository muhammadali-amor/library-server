package it.library_server.payload;

import it.library_server.entity.enums.Age;
import it.library_server.entity.enums.Genre;

import java.util.List;

public record BookDto(
        Long id,
        String name,
        String author,
        int publishedYear,
        String language,
        Age age,
        String bookPdfName,
        String description,
        List<Genre> genre
) {
}
