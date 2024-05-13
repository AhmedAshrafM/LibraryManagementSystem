package org.maidscc.librarymanagementsystem.dtos;

public record BookDTO (
        String title,
        String author,
        String publicationYear,
        String isbn,
        String publisher,
        String genre) {
}
