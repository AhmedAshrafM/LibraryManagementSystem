package org.maidscc.librarymanagementsystem.converters;

import org.maidscc.librarymanagementsystem.dtos.BookDTO;
import org.maidscc.librarymanagementsystem.models.Book;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BookToBookDtoConverter implements Converter<Book, BookDTO> {
    @Override
    public BookDTO convert(Book source) {

        BookDTO bookDTO = new BookDTO(source.getTitle(),
                source.getAuthor(),
                source.getPublicationYear(),
                source.getIsbn(),
                source.getPublisher(),
                source.getGenre(),
                source.getStock());

        return bookDTO;
    }
}
