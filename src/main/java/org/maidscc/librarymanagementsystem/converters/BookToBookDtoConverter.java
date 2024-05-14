package org.maidscc.librarymanagementsystem.converters;

import org.maidscc.librarymanagementsystem.dtos.BookRequestDTO;
import org.maidscc.librarymanagementsystem.models.Book;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BookToBookDtoConverter implements Converter<Book, BookRequestDTO> {
    @Override
    public BookRequestDTO convert(Book source) {

        BookRequestDTO bookDTO = new BookRequestDTO(source.getTitle(),
                source.getAuthor(),
                source.getPublicationYear(),
                source.getIsbn(),
                source.getPublisher(),
                source.getGenre(),
                source.getStock());

        return bookDTO;
    }
}
