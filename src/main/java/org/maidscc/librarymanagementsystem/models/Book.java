package org.maidscc.librarymanagementsystem.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;

import java.util.Optional;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "title")
    @NotNull
    private String title;
    @NotNull
    @Column(name = "author")
    private String author;
    @Column(name = "publication_year")
    private String publicationYear;
    @NotNull
    @Column(name = "isbn")
    private String isbn;
    @NotNull
    @Column(name = "publisher")
    private String publisher;
    @Column(name = "genre")
    private String genre;
    @Column(name = "stock")
    @Value("0")
    private Integer stock;

    public Book() {
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(String publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void incrementStock() {
        this.stock = Optional.ofNullable(this.stock).orElse(0) + 1;
    }

    public void decrementStock() {
        if (Optional.ofNullable(this.stock).orElse(0).equals(0))
            return;
        this.stock -= 1;
    }

}
