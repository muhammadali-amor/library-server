package it.library_server.service;

import it.library_server.entity.Book;
import it.library_server.implement.service.BookServiceImpl;
import it.library_server.payload.ApiResponse;
import it.library_server.payload.BookDto;
import it.library_server.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class BookService implements BookServiceImpl {
    private static final Logger logger = LoggerFactory.getLogger(BookService.class);
    private final BookRepository bookRepository;

    @Override
    public List<BookDto> getBooks() {
        List<BookDto> bookDtos = new ArrayList<>();
        for (Book book : bookRepository.findAll()) {
            BookDto build = BookDto.builder()
                    .id(book.getId())
                    .name(book.getName())
                    .author(book.getAuthor())
                    .description(book.getDescription())
                    .publishedYear(book.getPublisherYear())
                    .language(book.getLanguage())
                    .age(book.getAge())
                    .bookPdfName(book.getBookPdfName())
                    .genre(book.getGenres())
                    .build();
            bookDtos.add(build);
        }
        return bookDtos;
    }

    @Override
    public ApiResponse<?> addBook(BookDto bookDto) {
        try {
            boolean existsByName = bookRepository.existsByNameIgnoreCase(bookDto.name());
            if (!existsByName) {
                Book book = Book.builder()
                        .author(bookDto.author())
                        .description(bookDto.description())
                        .language(bookDto.language())
                        .publisherYear(bookDto.publishedYear())
                        .language(bookDto.language())
                        .age(bookDto.age())
                        .bookPdfName(bookDto.bookPdfName())
                        .build();
                book.setName(bookDto.name());
                bookRepository.save(book);
                logger.info("Book added successfully");
                return new ApiResponse<>("Yangi kitob saqlandi.", true);
            } else {
                return new ApiResponse<>("Bu kitob oldindan mavjud!", false);
            }
        } catch (Exception e) {
            return new ApiResponse<>("error -> " + e.getMessage(), false);
        }
    }

    @Override
    public ApiResponse<?> updateBook(BookDto bookDto) {
        return null;
    }

    @Override
    public ApiResponse<?> deleteBook(Long id) {
        try {
            if (bookRepository.existsById(id)){
                bookRepository.deleteById(id);
                logger.info("Book deleted successfully");
                return new ApiResponse<>("Kitob o'chirildi.", true);
            } else {
                logger.error("Book not found");
                return new ApiResponse<>("Kitob topilmadi!", false);
            }
        } catch (Exception e) {
            logger.error("error -> " + e.getMessage(), e);
            return new ApiResponse<>("error -> " + e.getMessage(), false);
        }
    }

    @Override
    public Book getBook(Long id) {
        return null;
    }
}
