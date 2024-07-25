package com.springboot.demo.bookstore.components.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.springboot.demo.bookstore.entity.Author;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

/**
 * This class contains the properties of a Book return response
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookResponse {

    private String title;

    private String isbn;

    private Set<AuthorResponse> authors;

    private String year;

    private String price;

    private String genre;

}
