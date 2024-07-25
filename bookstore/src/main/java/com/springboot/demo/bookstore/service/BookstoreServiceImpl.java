package com.springboot.demo.bookstore.service;

import com.springboot.demo.bookstore.components.requests.BookRequest;
import com.springboot.demo.bookstore.components.responses.BookResponse;
import com.springboot.demo.bookstore.entity.Author;
import com.springboot.demo.bookstore.entity.Book;
import com.springboot.demo.bookstore.exception.BookstoreException;
import com.springboot.demo.bookstore.exception.BookstoreExceptionConstants;
import com.springboot.demo.bookstore.repository.BookRepository;
import com.springboot.demo.bookstore.utils.BookSpecificationsUtil;
import com.springboot.demo.bookstore.utils.IsbnGeneratorServiceUtil;
import com.springboot.demo.bookstore.utils.MapperUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * The class provides the implementation for the BookstoreService interface.
 */
@Slf4j
@Service
@AllArgsConstructor
public class BookstoreServiceImpl implements BookstoreService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorService authorService;

    /**
     * This method finds the list of books based on the user input
     * @param title - The title
     * @param author -The author
     * @param searchType - AND/OR
     * @return List of BookResponse objects
     */
    public List<BookResponse> findBooks(String title, String author, String searchType) {
        Specification<Book> spec = BookSpecificationsUtil.byTitleOrAuthorName(title, author, searchType);

        log.info("Fetching the books from database");
        List<Book> bookList = bookRepository.findAll(spec);
        if(bookList.isEmpty())
            throw new BookstoreException(HttpStatus.BAD_REQUEST, BookstoreExceptionConstants.BOOK_NOT_FOUND.getDescription());

        log.info("Returning the book list");
        return MapperUtil.convertToBookResponseList(bookList);
    }

    /**
     * This method saves the book entity into the database
     * @param bookRequest - The book request input
     * @return BookResponse object
     */
    @Transactional
    public BookResponse saveBook(BookRequest bookRequest) {
        Book book = MapperUtil.convertRequestToBook(bookRequest);

        Set<Author> authors = authorService.fetchAuthors(bookRequest.getAuthors());
        book.setAuthors(authors);

        String isbn = IsbnGeneratorServiceUtil.generatorIsbn13(bookRequest.getTitle());
        book.setIsbn(isbn);

        log.info("Saving the book");
        book = bookRepository.save(book);
        log.info("Returning the saved book");
        return MapperUtil.convertToBookResponse(book);
    }

    /**
     * This method updates the book entity into the database based on the unique ISBN
     * @param bookRequest - The book request input
     * @return BookResponse object
     */
    @Transactional
    public BookResponse updateBook(BookRequest bookRequest) {
        Book existingBook = bookRepository.findByIsbn(bookRequest.getIsbn())
                .orElseThrow(() -> new BookstoreException(HttpStatus.BAD_REQUEST, BookstoreExceptionConstants.BOOK_NOT_FOUND.getDescription()));

        Book book = MapperUtil.convertRequestToBook(bookRequest);
        book.setIsbn(existingBook.getIsbn());
        book.setId(existingBook.getId());
        Set<Author> authors = authorService.fetchAuthors(bookRequest.getAuthors());
        book.setAuthors(authors);

        log.info("Updating the book");
        book = bookRepository.save(book);
        log.info("Returning the updated book");
        return MapperUtil.convertToBookResponse(book);
    }

    /**
     * This method deleted the book entity from the database based on the unique ISBN
     * @param isbn The unique ISBN string
     */
    @Transactional
    public void deleteBook(String isbn) {

        log.info("Searching for the book");
        if(!bookRepository.existsByIsbn(isbn))
            throw new BookstoreException(HttpStatus.BAD_REQUEST, BookstoreExceptionConstants.BOOK_NOT_FOUND.getDescription());
        log.info("Deleting the book");
        bookRepository.deleteByIsbn(isbn);
    }

}
