package it.library_server.controller;

import it.library_server.entity.Book;
import it.library_server.implement.controller.BookControllerImpl;
import it.library_server.payload.ApiResponse;
import it.library_server.payload.BookDto;
import it.library_server.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/book")
public class BookController implements BookControllerImpl {

    private final BookService bookService;

    @Override
    @GetMapping
    public HttpEntity<List<BookDto>> getBooks() {
        List<BookDto> books = bookService.getBooks();
        return ResponseEntity.ok(books);
    }

    @Override
    @GetMapping("/{id}")
    public HttpEntity<Book> getBook(@PathVariable Long id) {
        Book book = bookService.getBook(id);
        return ResponseEntity.ok(book);
    }

    @Override
    @PostMapping
    public HttpEntity<?> addBook(@RequestBody BookDto bookDto) {
        ApiResponse<?> response = bookService.addBook(bookDto);
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(response);
    }

    @Override
    @PutMapping("/{id}")
    public HttpEntity<?> updateBook(@RequestBody BookDto bookDto, @PathVariable Long id) {
        ApiResponse<?> response = bookService.updateBook(bookDto, id);
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(response);
    }

    @Override
    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteBook(@PathVariable Long id) {
        ApiResponse<?> response = bookService.deleteBook(id);
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(response);
    }
}
