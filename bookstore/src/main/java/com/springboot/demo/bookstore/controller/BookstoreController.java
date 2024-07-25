package com.springboot.demo.bookstore.controller;

import com.springboot.demo.bookstore.components.requests.BookRequest;
import com.springboot.demo.bookstore.components.responses.BookResponse;
import com.springboot.demo.bookstore.exception.BookstoreException;
import com.springboot.demo.bookstore.exception.BookstoreExceptionConstants;
import com.springboot.demo.bookstore.service.BookstoreService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This class contain the Bookstore controller api endpoints
 */
@Slf4j
@RestController
@RequestMapping(value = "/books")
public class BookstoreController {

    @Autowired
    BookstoreService bookstoreService;

    @PostMapping()
    public ResponseEntity<BookResponse> saveBook(@Valid @RequestBody BookRequest bookRequest) {
        log.info("Saving a new book {}", bookRequest.getTitle());
        return ResponseEntity.ok(bookstoreService.saveBook(bookRequest));
    }

    @PutMapping()
    public ResponseEntity<BookResponse> updateBook(@RequestBody BookRequest bookRequest) {
        log.info("Updating a book {}", bookRequest.getTitle());
        return ResponseEntity.ok(bookstoreService.updateBook(bookRequest));
    }

    @GetMapping("/search")
    public ResponseEntity<List<BookResponse>> searchBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(defaultValue = "AND") String searchType) {
        log.info("Searching for books with title {} and author {}", title, author);

        if(title==null && author==null)
            throw new BookstoreException(HttpStatus.BAD_REQUEST, BookstoreExceptionConstants.INVALID_PARAMETERS.getDescription());

        if (("AND".equalsIgnoreCase(searchType) || "OR".equalsIgnoreCase(searchType))) {
            List<BookResponse> books = bookstoreService.findBooks(title, author, searchType);
            return ResponseEntity.ok(books);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping()
    public ResponseEntity deleteBook(@RequestParam(required = true) String isbn) {
        try {
            log.info("Deleting a book");
            bookstoreService.deleteBook(isbn);
            return ResponseEntity.ok().build();
        } catch (Exception e){
            return ResponseEntity.badRequest().body(BookstoreExceptionConstants.BOOK_NOT_FOUND.getDescription());
        }
    }
}
