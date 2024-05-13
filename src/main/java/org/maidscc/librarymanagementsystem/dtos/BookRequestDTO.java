package org.maidscc.librarymanagementsystem.dtos;

public record BookRequestDTO(
        String title,
        String author,
        String publicationYear,
        String isbn,
        String publisher,
        String genre,
        Integer stock) {
}
