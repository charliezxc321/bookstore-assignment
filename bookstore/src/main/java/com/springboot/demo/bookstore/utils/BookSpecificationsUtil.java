package com.springboot.demo.bookstore.utils;

import com.springboot.demo.bookstore.entity.Author;
import com.springboot.demo.bookstore.entity.Book;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

/**
 * This class defines static methods for constructing JPA Specifications for dynamic query for the author and/or title columns
 */
public class BookSpecificationsUtil {

    public static Specification<Book> byTitle(String title) {
        return (root, query, criteriaBuilder) -> {
            if (title == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("title"), title);
        };
    }

    public static Specification<Book> byAuthorName(String author) {
        return (root, query, criteriaBuilder) -> {
            if (author == null) {
                return criteriaBuilder.conjunction();
            }
            Join<Book, Author> authors = root.join("authors", JoinType.INNER);
            return criteriaBuilder.equal(authors.get("name"), author);
        };
    }

    public static Specification<Book> byTitleOrAuthorName(String title, String author, String searchType) {
        List<Specification<Book>> specs = new ArrayList<>();
        if (title != null) {
            specs.add(byTitle(title));
        }
        if (author != null) {
            specs.add(byAuthorName(author));
        }

        Specification<Book> result = specs.get(0);
        if ("AND".equalsIgnoreCase(searchType)) {
            for (int i = 1; i < specs.size(); i++) {
                result = result.and(specs.get(i));
            }
        } else {
            for (int i = 1; i < specs.size(); i++) {
                result = result.or(specs.get(i));
            }
        }
        return result;
    }
}
