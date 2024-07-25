package com.springboot.demo.bookstore.exception;

import com.fasterxml.jackson.databind.util.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

/**
 * This class listens for any exception thrown during runtime and handles them accordingly
 */
@Slf4j
@RestControllerAdvice
public class BookstoreErrorHandler {

    @ExceptionHandler(BookstoreException.class)
    public ResponseEntity<Map<String, String>> handleBookstoreException(BookstoreException ex) {
        log.error("Error while processing bookstore application : " + ex);
        return ResponseEntity.status(ex.getStatus()).body(Map.of("message", ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleValidationException(Exception ex) {
        log.error("Error while processing bookstore application : " + ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", BookstoreExceptionConstants.OTHER_ERROR.getDescription()));
    }
}
