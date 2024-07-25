package com.springboot.demo.bookstore.service;

import com.springboot.demo.bookstore.components.requests.BookRequest;
import com.springboot.demo.bookstore.components.responses.AuthorResponse;
import com.springboot.demo.bookstore.components.responses.BookResponse;
import com.springboot.demo.bookstore.entity.Author;
import com.springboot.demo.bookstore.entity.Book;
import com.springboot.demo.bookstore.exception.BookstoreException;
import com.springboot.demo.bookstore.repository.BookRepository;
import com.springboot.demo.bookstore.utility.BuilderUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class BookstoreServiceImplTest {
    @MockBean
    private BookRepository bookRepository;
    @MockBean
    private AuthorService authorService;

    @Autowired
    private BookstoreService bookstoreService;

    @Test
    public void findBooksNegativeTest() {
        Mockito.when(bookRepository.findAll((Specification<Book>) any())).thenReturn(new ArrayList<Book>());

        BookstoreException bookstoreException = Assertions.assertThrows(BookstoreException.class, () ->
                bookstoreService.findBooks ("test-title", "test-author", "OR"));
        HttpStatus status = bookstoreException.getStatus();
        String message = bookstoreException.getMessage();

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, status);
        Assertions.assertEquals("Book is not found", message);
    }
    @Test
    public void findBooksPositiveTest() {
        Mockito.when(bookRepository.findAll((Specification<Book>) any())).thenReturn(BuilderUtil.buildBookList());

        List<BookResponse> bookList = bookstoreService.findBooks ("test-title", "test-author", "OR");

        Assertions.assertEquals(1, bookList.size());
    }

    @Test
    public void saveBookPositiveTest() {
        Mockito.when(bookRepository.save(any())).thenReturn(BuilderUtil.buildBook());
        Mockito.when(authorService.fetchAuthors(any())).thenReturn(BuilderUtil.buildAuthorList());

        BookResponse bookResponse = bookstoreService.saveBook(BuilderUtil.buildBookRequest());

        Assertions.assertEquals("test-title", bookResponse.getTitle());
    }
//
    @Test
    public void updateBookNegativeTest() {

        BookstoreException bookstoreException = Assertions.assertThrows(BookstoreException.class, () ->
                bookstoreService.updateBook (BuilderUtil.buildBookRequest()));
        HttpStatus status = bookstoreException.getStatus();
        String message = bookstoreException.getMessage();

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, status);
        Assertions.assertEquals("Book is not found", message);
    }
    @Test
    @Transactional
    public void updateBookPositiveTest() {
        Mockito.when(bookRepository.save(any())).thenReturn(BuilderUtil.buildBook());
        Mockito.when(bookRepository.findByIsbn(any())).thenReturn(Optional.ofNullable(BuilderUtil.buildBook()));
        Mockito.when(authorService.fetchAuthors(any())).thenReturn(BuilderUtil.buildAuthorList());
        BookResponse bookResponse = bookstoreService.saveBook(BuilderUtil.buildBookRequest());

        BookRequest updateBookRequest = BuilderUtil.buildBookRequest();
        updateBookRequest.setIsbn(bookResponse.getIsbn());
        BookResponse updateBookResponse = bookstoreService.updateBook(updateBookRequest);

        Assertions.assertEquals("test-title", updateBookResponse.getTitle());
    }
    @Test
    public void deleteBookNegativeTest() {
        Mockito.when(bookRepository.existsByIsbn(any())).thenReturn(false);
        String isbn = "1212121212133";
        BookstoreException bookstoreException = Assertions.assertThrows(BookstoreException.class, () ->
                bookstoreService.deleteBook(isbn));
        HttpStatus status = bookstoreException.getStatus();
        String message = bookstoreException.getMessage();

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, status);
        Assertions.assertEquals("Book is not found", message);
    }
    @Test
    public void deleteBookPositiveTest() {
        Mockito.when(bookRepository.existsByIsbn(any())).thenReturn(true);
        Mockito.doNothing().when(bookRepository).deleteByIsbn(any());
        String isbn = "1212121212121";
        bookstoreService.deleteBook(isbn);

        Mockito.verify(bookRepository, times(1)).deleteByIsbn(isbn);
    }
}
