package it.library_server.payload;

import java.util.UUID;

public record FavouriteBooksDto(
   Long id,
   UUID userId,
   Long bookId
) {}
