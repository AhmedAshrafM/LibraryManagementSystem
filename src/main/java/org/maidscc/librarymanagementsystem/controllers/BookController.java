package org.maidscc.librarymanagementsystem.controllers;

import jakarta.validation.Valid;
import org.maidscc.librarymanagementsystem.dtos.BookDTO;
import org.maidscc.librarymanagementsystem.exceptions.BookNotFoundException;
import org.maidscc.librarymanagementsystem.exceptions.DuplicateFoundException;
import org.maidscc.librarymanagementsystem.models.Book;
import org.maidscc.librarymanagementsystem.services.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> getBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("{bookId}")
    public Book getBookById(
            @PathVariable("bookId") long bookId
    ) {
        try {
            return bookService.getBookById(bookId);
        } catch (BookNotFoundException e) {
            throw new BookNotFoundException(e.getMessage());
        }
    }

    @PostMapping
    public Book addBook(@RequestBody @Valid BookDTO book) {
        try {
            Book newBook = bookService.addBook(book);
            if (newBook == null) {
                ResponseEntity.badRequest().build();
            }
            return newBook;
        } catch (DuplicateFoundException e) {
            throw new DuplicateFoundException(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public Book updateBook(@PathVariable Long id, @RequestBody Book book) {
        try {
            Book updatedBook = bookService.updateBook(id, book);

            if (updatedBook == null) {
                ResponseEntity.badRequest().build();
            }

            return updatedBook;
        } catch (BookNotFoundException e) {
            throw new BookNotFoundException(e.getMessage());
        } catch (DuplicateFoundException e) {
            throw new DuplicateFoundException(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public void deleteBook(@PathVariable Long id) {
        try {
            bookService.deleteBook(id);

        } catch (BookNotFoundException e) {
            throw new BookNotFoundException(e.getMessage());
        }
    }
}
