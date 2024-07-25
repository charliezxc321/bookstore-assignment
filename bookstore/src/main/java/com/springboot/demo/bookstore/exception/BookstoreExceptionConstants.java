package com.springboot.demo.bookstore.exception;

import lombok.Getter;

/**
 * This class contains the bookstore exception constants
 */
@Getter
public enum BookstoreExceptionConstants {
    INVALID_PARAMETERS("Invalid Parameters"),
    BOOK_NOT_FOUND("Book is not found"),
    BOOK_EXISTS("Book already exists"),
    AUTHOR_NOT_FOUND("Author is not found"),
    USER_NOT_FOUND("User is not found"),
    OTHER_ERROR("Server error, please contact helpdesk for assistance");

    String description;
    BookstoreExceptionConstants(String description) {
        this.description = description;
    }
}
