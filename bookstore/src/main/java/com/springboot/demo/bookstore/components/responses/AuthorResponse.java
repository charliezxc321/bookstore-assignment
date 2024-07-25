package com.springboot.demo.bookstore.components.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class contains the properties of an author return response
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthorResponse {
    private String name;
    private String birthday;
}
