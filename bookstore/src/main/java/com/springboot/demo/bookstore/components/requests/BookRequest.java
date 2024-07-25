package com.springboot.demo.bookstore.components.requests;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * This class contains the properties of a Book creation/update payload request
 */
@Data
@Builder
public class BookRequest {

    private Long id;
    private String isbn;

    @NotNull
    @NotBlank
    @Size(max=50)
    private String title;

    @NotEmpty
    private List<Long> authors;

    @NotNull
    @Positive
    private int year;

    @NotNull
    @Positive
    private double price;

    @NotNull
    @NotBlank
    @Size(min=1 ,max=10)
    private String genre;
}
