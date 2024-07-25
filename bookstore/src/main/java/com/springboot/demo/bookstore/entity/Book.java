package com.springboot.demo.bookstore.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

/**
 * This class contain the DB model of the Book entity
 */
@Data
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="isbn")
    private String isbn;

    @Column(name= "title")
    private String title;

    @ManyToMany
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private Set<Author> authors;

    @Column(name= "year_of_publication")
    private int year;

    @Column(name= "price")
    private double price;

    @Column(name= "genre")
    private String genre;

}
