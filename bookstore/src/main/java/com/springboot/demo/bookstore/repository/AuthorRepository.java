package com.springboot.demo.bookstore.repository;

import com.springboot.demo.bookstore.entity.Author;
import com.springboot.demo.bookstore.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * This class serves as a repository for managing the Author entities
 */
@Repository
public interface AuthorRepository extends JpaRepository<Author, Long>, JpaSpecificationExecutor<Author> {
    List<Author> findAll();
}
