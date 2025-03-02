package it.library_server.implement.controller;

import it.library_server.payload.ApiResponse;
import it.library_server.payload.BookDto;
import org.springframework.http.HttpEntity;

import java.util.List;

public interface BookControllerImpl {
    HttpEntity<?> addBook(BookDto bookDto);
    HttpEntity<?> updateBook(BookDto bookDto);
    HttpEntity<?> deleteBook(BookDto bookDto);
    List<BookDto> getBook();
    BookDto getOneBook();
}
