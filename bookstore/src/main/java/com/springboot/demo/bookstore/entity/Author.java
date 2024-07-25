package com.springboot.demo.bookstore.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * This class contain the DB model of the Author entity
 */
@Data
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="author")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name= "name")
    private String name;

    @Column(name= "birthday")
    private String birthday;

}
