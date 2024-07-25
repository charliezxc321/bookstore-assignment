package com.springboot.demo.bookstore.service;

import com.springboot.demo.bookstore.entity.Author;
import com.springboot.demo.bookstore.exception.BookstoreException;
import com.springboot.demo.bookstore.exception.BookstoreExceptionConstants;
import com.springboot.demo.bookstore.repository.AuthorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The class provides the implementation for the Author Service interface.
 */
@Slf4j
@Service
public class AuthorServiceImpl implements AuthorService{

    @Autowired
    private AuthorRepository authorRepository;

    /**
     * This method fetches the list of authors and returns them in a set.
     * @param authors - The authors
     * @return Set of authors
     */
    public Set<Author> fetchAuthors(List<Long> authors) {
        Set<Author> authorSet = new HashSet<>();
        log.info("Fetching the authors");
        for(Long id : authors) {
            Author author = authorRepository.findById(id)
                    .orElseThrow(() -> new BookstoreException(HttpStatus.BAD_REQUEST, BookstoreExceptionConstants.AUTHOR_NOT_FOUND.getDescription()));
            authorSet.add(author);
        }
        log.info("Returning the authors");
        return authorSet;
    }
}
