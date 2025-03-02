package it.library_server.controller;

import it.library_server.entity.Book;
import it.library_server.payload.ApiResponse;
import it.library_server.payload.BookDto;
import it.library_server.service.FavouriteBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/favourite-book")
@RequiredArgsConstructor
public class FavouriteBookController {

    private final FavouriteBookService favouriteBookService;

    @GetMapping("/{userId}")
    public HttpEntity<?> getFavouriteBooks(@PathVariable UUID userId) {
        List<BookDto> favouriteBooks = favouriteBookService.getFavouriteBooks(userId);
        return ResponseEntity.ok(favouriteBooks);
    }

    @PostMapping
    public HttpEntity<?> toggleFavouriteBook(@RequestParam(name = "user-id") UUID userId, @RequestParam("book-id") Long bookId) {
        ApiResponse<?> toggleFavourite = favouriteBookService.toggleFavourite(userId, bookId);
        return ResponseEntity.status(toggleFavourite.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(toggleFavourite);
    }

}
