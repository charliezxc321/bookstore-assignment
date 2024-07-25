package com.springboot.demo.bookstore.utils;

import com.springboot.demo.bookstore.components.requests.BookRequest;
import com.springboot.demo.bookstore.components.responses.AuthorResponse;
import com.springboot.demo.bookstore.components.responses.BookResponse;
import com.springboot.demo.bookstore.entity.Author;
import com.springboot.demo.bookstore.entity.Book;
import com.springboot.demo.bookstore.mapper.BookMapper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class contains the mapper method utilities
 */
public class MapperUtil {

    public static Book convertRequestToBook(BookRequest bookRequest) {
        return BookMapper.Instance.mapRequestToEntity(bookRequest);
    }

    public static BookResponse convertToBookResponse(Book book) {
        BookResponse bookResponse = BookMapper.Instance.mapEntityToResponse(book);
        bookResponse.setAuthors(MapperUtil.convertToAuthorResponseList(book.getAuthors()));
        return bookResponse;
    }
    public static List<BookResponse> convertToBookResponseList(List<Book> bookList) {
        return  bookList.stream().map(BookMapper.Instance::mapEntityToResponse).toList();
    }

    public static Set<AuthorResponse> convertToAuthorResponseList(Set<Author> authorList) {
        Set<AuthorResponse> authorResponses = new HashSet<>();
        for(Author author : authorList)
            authorResponses.add(convertToAuthorResponse(author));
        return authorResponses;
    }

    public static AuthorResponse convertToAuthorResponse(Author author) {
        return AuthorResponse.builder().name(author.getName()).birthday(author.getBirthday()).build();
    }
}
