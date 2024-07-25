package com.springboot.demo.bookstore.mapper;

import com.springboot.demo.bookstore.components.requests.BookRequest;
import com.springboot.demo.bookstore.components.responses.AuthorResponse;
import com.springboot.demo.bookstore.components.responses.BookResponse;
import com.springboot.demo.bookstore.entity.Author;
import com.springboot.demo.bookstore.entity.Book;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-25T00:17:38+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
public class BookMapperImpl implements BookMapper {

    @Override
    public BookResponse mapEntityToResponse(Book book) {
        if ( book == null ) {
            return null;
        }

        BookResponse.BookResponseBuilder bookResponse = BookResponse.builder();

        bookResponse.isbn( book.getIsbn() );
        bookResponse.price( formatPrice( book.getPrice() ) );
        bookResponse.year( formatYear( book.getYear() ) );
        bookResponse.title( book.getTitle() );
        bookResponse.authors( authorSetToAuthorResponseSet( book.getAuthors() ) );
        bookResponse.genre( book.getGenre() );

        return bookResponse.build();
    }

    @Override
    public Book mapRequestToEntity(BookRequest bookRequest) {
        if ( bookRequest == null ) {
            return null;
        }

        Book.BookBuilder book = Book.builder();

        book.id( bookRequest.getId() );
        book.isbn( bookRequest.getIsbn() );
        book.title( bookRequest.getTitle() );
        book.year( bookRequest.getYear() );
        book.price( bookRequest.getPrice() );
        book.genre( bookRequest.getGenre() );

        return book.build();
    }

    protected AuthorResponse authorToAuthorResponse(Author author) {
        if ( author == null ) {
            return null;
        }

        AuthorResponse.AuthorResponseBuilder authorResponse = AuthorResponse.builder();

        authorResponse.name( author.getName() );
        authorResponse.birthday( author.getBirthday() );

        return authorResponse.build();
    }

    protected Set<AuthorResponse> authorSetToAuthorResponseSet(Set<Author> set) {
        if ( set == null ) {
            return null;
        }

        Set<AuthorResponse> set1 = new LinkedHashSet<AuthorResponse>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Author author : set ) {
            set1.add( authorToAuthorResponse( author ) );
        }

        return set1;
    }
}
