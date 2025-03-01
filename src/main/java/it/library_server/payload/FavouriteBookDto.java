package it.library_server.payload;

import lombok.Builder;

import java.util.UUID;

@Builder
public record FavouriteBookDto(
   Long id,
   UUID userId,
   Long bookId
) {}
