package com.springboot.demo.bookstore.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * This class contains the BookstoreException object
 */
@Getter
public class BookstoreException extends RuntimeException {
    private static long serialVersionUID = -100000L;

    private final HttpStatus status;

    public BookstoreException(HttpStatus status, String errorMessage) {
        super(errorMessage);
        this.status = status;
    }

}
