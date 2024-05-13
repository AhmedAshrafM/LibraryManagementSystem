package org.maidscc.librarymanagementsystem.converters;

import org.maidscc.librarymanagementsystem.dtos.BookRequestDTO;
import org.maidscc.librarymanagementsystem.models.Book;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BookDtoToBookConverter implements Converter<BookRequestDTO, Book> {

    @Override
    public Book convert(BookRequestDTO source) {
        Book book = new Book();
        book.setTitle(source.title());
        book.setAuthor(source.author());
        book.setPublicationYear(source.publicationYear());
        book.setIsbn(source.isbn());
        book.setPublisher(source.publisher());
        book.setGenre(source.genre());
        book.setStock(source.stock());
        return book;
    }
}
