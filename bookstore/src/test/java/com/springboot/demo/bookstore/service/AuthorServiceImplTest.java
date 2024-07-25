package com.springboot.demo.bookstore.service;

import com.springboot.demo.bookstore.components.responses.BookResponse;
import com.springboot.demo.bookstore.entity.Author;
import com.springboot.demo.bookstore.entity.Book;
import com.springboot.demo.bookstore.exception.BookstoreException;
import com.springboot.demo.bookstore.repository.AuthorRepository;
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

import java.util.*;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class AuthorServiceImplTest {

    @MockBean
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorService authorService;

    @Test
    public void fetchAuthorsNegativeTest() {
        Mockito.when(authorRepository.findById(any())).thenReturn(Optional.empty());
        
        BookstoreException bookstoreException = Assertions.assertThrows(BookstoreException.class, () ->
                authorService.fetchAuthors (BuilderUtil.buildAuthorRequest()));
        HttpStatus status = bookstoreException.getStatus();
        String message = bookstoreException.getMessage();

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, status);
        Assertions.assertEquals("Author is not found", message);
    }
    @Test
    public void fetchAuthorsPositiveTest() {
        Mockito.when(authorRepository.findById(any())).thenReturn(Optional.ofNullable(BuilderUtil.buildAuthor()));

        Set<Author> authorSet = authorService.fetchAuthors (BuilderUtil.buildAuthorRequest());

        Assertions.assertEquals(1, authorSet.size());
    }
}
