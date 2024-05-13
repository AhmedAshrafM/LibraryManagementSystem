package org.maidscc.librarymanagementsystem.converters;

import org.maidscc.librarymanagementsystem.dtos.BookResponseDTO;
import org.maidscc.librarymanagementsystem.models.Book;
import org.mapstruct.Mapper;

@Mapper
public interface BookDTOMapper {
    BookResponseDTO sourceToDestination(Book book);

    Book destinationToSource(BookResponseDTO bookDto);
}
