package com.springboot.demo.bookstore.mapper;

import com.springboot.demo.bookstore.components.requests.BookRequest;
import com.springboot.demo.bookstore.components.responses.BookResponse;
import com.springboot.demo.bookstore.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * This class contain the mapper for the Book entity
 * It maps the structure of BookRequest class to Book class
 * and the structure of Book class to BookResponse class
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookMapper {
    BookMapper Instance = Mappers.getMapper(BookMapper.class);
    @Mapping(target = "isbn", source ="isbn")
    @Mapping(source = "price", target = "price", qualifiedByName = "formatPrice")
    @Mapping(source = "year", target = "year", qualifiedByName = "formatYear")
    BookResponse mapEntityToResponse(Book book);

    @Mapping(target = "authors", ignore = true)
    Book mapRequestToEntity(BookRequest bookRequest);

    @Named("formatPrice")
    default String formatPrice(double price) {
        return String.format("%.2f", price);
    }

    @Named("formatYear")
    default String formatYear(int year) {
        return Integer.toString(year);
    }
}
