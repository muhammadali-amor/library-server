package it.library_server.service;

import it.library_server.entity.Book;
import it.library_server.exception.ResourceNotFoundException;
import it.library_server.implement.service.BookServiceImpl;
import it.library_server.payload.ApiResponse;
import it.library_server.payload.BookDto;
import it.library_server.repository.BookRepository;
import it.library_server.utils.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        return bookRepository.findAll().stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public ApiResponse<?> addBook(BookDto bookDto) {
        try {
            boolean existsByName = bookRepository.existsByNameIgnoreCase(bookDto.name());
            if (!existsByName) {
                Book book = bookBuilder(bookDto);
                book.setName(bookDto.name());
                bookRepository.save(book);
                logger.info(Messages.BOOK_SAVED);
                return new ApiResponse<>(Messages.BOOK_SAVED, true);
            } else {
                logger.warn(Messages.BOOK_ALREADY_EXISTS);
                return new ApiResponse<>(Messages.BOOK_ALREADY_EXISTS, false);
            }
        } catch (Exception e) {
            logger.error(Messages.ERROR_OBJ, e.getMessage(), e);
            return new ApiResponse<>("error -> " + e.getMessage(), false);
        }
    }

    @Override
    public ApiResponse<?> updateBook(BookDto bookDto, Long id) {
        try {
            Book book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(404, "getBook", "book", id));
            boolean isNameChanging = !book.getName().equalsIgnoreCase(bookDto.name());

            if (isNameChanging && bookRepository.existsByNameIgnoreCase(bookDto.name())) {
                logger.warn(Messages.BOOK_ALREADY_EXISTS);
                return new ApiResponse<>(Messages.BOOK_ALREADY_EXISTS, false);
            } else {
                book.setName(bookDto.name());
                book.setAuthor(bookDto.author());
                book.setDescription(bookDto.description());
                book.setLanguage(bookDto.language());
                book.setCoverImage(bookDto.coverImage());
                book.setPublishedYear(bookDto.publishedYear());
                book.setAge(bookDto.age());
                book.setBookPdfName(bookDto.bookPdfName());
                book.setGenres(bookDto.genres());
                bookRepository.save(book);
                logger.info(Messages.BOOK_SAVED);
                return new ApiResponse<>(Messages.BOOK_SAVED, true);
            }
        } catch (Exception e) {
            logger.error(Messages.ERROR_OBJ, e.getMessage(), e);
            return new ApiResponse<>("error -> " + e.getMessage(), false);
        }
    }

    @Override
    public ApiResponse<?> deleteBook(Long id) {
        try {
            Book book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(404, "deleteBook", "book", id));
            bookRepository.delete(book);
            logger.info(Messages.BOOK_DELETED);
            return new ApiResponse<>(Messages.BOOK_DELETED, true);
        } catch (Exception e) {
            logger.error(Messages.ERROR_OBJ, e.getMessage(), e);
            return new ApiResponse<>("error -> " + e.getMessage(), false);
        }
    }

    @Override
    public Book getBook(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(404, "getBook", "id", id));
    }

    public Book bookBuilder(BookDto bookDto) {
        return Book.builder()
                .author(bookDto.author())
                .publishedYear(bookDto.publishedYear())
                .language(bookDto.language())
                .age(bookDto.age())
                .coverImage(bookDto.coverImage())
                .description(bookDto.description())
                .bookPdfName(bookDto.bookPdfName())
                .genres(bookDto.genres())
                .build();

    }

    private BookDto convertToDto(Book book) {
        return BookDto.builder()
                .id(book.getId())
                .name(book.getName())
                .author(book.getAuthor())
                .description(book.getDescription())
                .publishedYear(book.getPublishedYear())
                .language(book.getLanguage())
                .age(book.getAge())
                .bookPdfName(book.getBookPdfName())
                .genres(book.getGenres())
                .build();
    }

}
