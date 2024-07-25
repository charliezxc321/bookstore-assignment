package com.springboot.demo.bookstore.utility;

import com.springboot.demo.bookstore.components.requests.BookRequest;
import com.springboot.demo.bookstore.components.responses.AuthorResponse;
import com.springboot.demo.bookstore.components.responses.BookResponse;
import com.springboot.demo.bookstore.entity.Author;
import com.springboot.demo.bookstore.entity.Book;
import com.springboot.demo.bookstore.entity.UserDetail;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class contain the builder method utilities, it will help to build the objects required for testing
 */
public class BuilderUtil {

    public static BookRequest buildBookRequest() {
        return BookRequest.builder().title("test-title").authors(buildAuthorRequest()).price(50).year(2000).genre("test-genre").build();
    }

    public static BookResponse buildBookResponse() {
        return BookResponse.builder().title("test-title").authors(buildAuthorResponses()).price("50.00").year("2000").genre("test-genre").build();
    }

    public static List<BookResponse> buildBookResponses() {
        List<BookResponse> bookResponses = new ArrayList<>();
        bookResponses.add(buildBookResponse());
        return bookResponses;
    }

    public static List<Book> buildBookList() {
        List<Book> bookList = new ArrayList<>();
        Book book = buildBook();
        bookList.add(book);
        return bookList;
    }

    public static Book buildBook() {
        return Book.builder().title("test-title").price(50).isbn("5555555555555").genre("test-genre").year(2000).authors(buildAuthorList()).build();
    }

    public static List<Long> buildAuthorRequest() {
        List<Long> authorRequest = new ArrayList<>();
        authorRequest.add(1L);
        return authorRequest;
    }
    private static Set<AuthorResponse> buildAuthorResponses() {
        Set<AuthorResponse> authorResponse = new HashSet<>();
        authorResponse.add(buildAuthorResponse());
        return authorResponse;
    }

    private static AuthorResponse buildAuthorResponse() {
        return AuthorResponse.builder().birthday("2020-01-01").name("test-name").build();
    }

    public static Set<Author> buildAuthorList() {
        Set<Author> authorResponse = new HashSet<>();
        authorResponse.add(buildAuthor());
        return authorResponse;
    }

    public static Author buildAuthor() {
        return Author.builder().birthday("2020-01-01").name("test-name").build();
    }

    public static UserDetail buildUserDetail() {
        return UserDetail.builder().id(1L).username("test-username").password("aa").role("test-role").build();
    }
}
