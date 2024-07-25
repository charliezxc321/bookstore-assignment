package com.springboot.demo.bookstore.service;

import com.springboot.demo.bookstore.components.requests.BookRequest;
import com.springboot.demo.bookstore.components.responses.BookResponse;

import java.util.List;

/**
 * This class defines the contract for business logic operations for Bookstore services
 */
public interface BookstoreService {
    List<BookResponse> findBooks(String title, String author, String searchType);

    BookResponse saveBook(BookRequest bookRequest);
    BookResponse updateBook(BookRequest bookRequest);
    void deleteBook(String isbn);
}
