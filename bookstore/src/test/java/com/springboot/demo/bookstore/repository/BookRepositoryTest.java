package com.springboot.demo.bookstore.repository;

import com.springboot.demo.bookstore.entity.Author;
import com.springboot.demo.bookstore.entity.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @Test
    public void findByIsbnNegativeTest() {
        String isbn = "4444444444444";

        Optional<Book> bookOptional = bookRepository.findByIsbn(isbn);
        Assertions.assertTrue(bookOptional.isEmpty());
    }

    @Test
    public void findByIsbnPositiveTest() {
        String isbn = "2222222222222";
        Book book = Book.builder().title("test-title").price(50).isbn(isbn).genre("test-genre").year(2000).build();
        bookRepository.save(book);

        Optional<Book> bookOptional = bookRepository.findByIsbn(isbn);

        Assertions.assertTrue(bookOptional.isPresent());
    }

    @Test
    public void existsByIsbnNegativeTest() {
        String isbn = "0000000000000";

        boolean bookOptional = bookRepository.existsByIsbn(isbn);

        Assertions.assertFalse(bookOptional);
    }

    @Test
    public void existsByIsbnPositiveTest() {
        String isbn = "1111111111111";
        Book book = Book.builder().title("test-title").price(50).isbn(isbn).genre("test-genre").year(2000).build();

        bookRepository.save(book);
        boolean bookOptional = bookRepository.existsByIsbn(isbn);

        Assertions.assertTrue(bookOptional);
    }

    @Test
    @Transactional
    public void deleteByIsbnTest() {
        String isbn = "3333333333333";
        Book book = Book.builder().title("test-title").price(50).isbn(isbn).genre("test-genre").year(2000).build();
        bookRepository.save(book);

        bookRepository.deleteByIsbn(isbn);

        Assertions.assertFalse(bookRepository.existsByIsbn(isbn));
    }

}
