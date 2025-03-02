package it.library_server.controller;

import it.library_server.implement.controller.BookControllerImpl;
import it.library_server.payload.ApiResponse;
import it.library_server.payload.BookDto;
import it.library_server.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/book")
public class BookController implements BookControllerImpl {

    private final BookService bookService;

    @Override
    @PostMapping("/add-book")
    public HttpEntity<?> addBook(BookDto bookDto) {
        ApiResponse<?> response = bookService.addBook(bookDto);
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(response);
    }

    @Override
    public HttpEntity<?> updateBook(BookDto bookDto) {
        return null;
    }

    @Override
    public HttpEntity<?> deleteBook(BookDto bookDto) {
        return null;
    }

    @Override
    public List<BookDto> getBook() {
        return List.of();
    }

    @Override
    public BookDto getOneBook() {
        return null;
    }
}
