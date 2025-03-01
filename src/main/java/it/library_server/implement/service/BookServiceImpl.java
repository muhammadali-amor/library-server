package it.library_server.implement.service;

import it.library_server.entity.Book;
import it.library_server.payload.ApiResponse;
import it.library_server.payload.BookDto;

import java.util.List;

public interface BookServiceImpl {
    public List<BookDto> getBooks();
    public ApiResponse<?> addBook(BookDto bookDto);
    public ApiResponse<?> updateBook(BookDto bookDto, Long id);
    public ApiResponse<?> deleteBook(Long id);
    public Book getBook(Long id);
}
