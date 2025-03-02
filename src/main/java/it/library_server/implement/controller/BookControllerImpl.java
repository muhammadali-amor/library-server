package it.library_server.implement.controller;

import it.library_server.entity.Book;
import it.library_server.payload.BookDto;
import org.springframework.http.HttpEntity;

import java.util.List;

public interface BookControllerImpl {
    HttpEntity<?> addBook(BookDto bookDto);
    HttpEntity<?> updateBook(BookDto bookDto, Long id);
    HttpEntity<?> deleteBook(Long id);
    List<BookDto> getBooks();
    Book getBook(Long id);
}
