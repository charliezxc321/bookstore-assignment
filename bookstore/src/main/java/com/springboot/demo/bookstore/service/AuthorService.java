package com.springboot.demo.bookstore.service;

import com.springboot.demo.bookstore.entity.Author;

import java.util.List;
import java.util.Set;

/**
 * This class defines the contract for business logic operations for Author services
 */
public interface AuthorService {
    Set<Author> fetchAuthors(List<Long> authors);
}
