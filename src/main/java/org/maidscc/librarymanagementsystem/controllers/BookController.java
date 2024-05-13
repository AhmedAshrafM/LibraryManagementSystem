package org.maidscc.librarymanagementsystem.controllers;

import jakarta.validation.Valid;
import org.maidscc.librarymanagementsystem.converters.BookDtoToBookConverter;
import org.maidscc.librarymanagementsystem.dtos.BookRequestDTO;
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
    public ResponseEntity<Book> getBookById(
            @PathVariable("bookId") long bookId
    ) {
        var queriedBook = bookService.getBookById(bookId);
        return ResponseEntity.ok(queriedBook);

    }

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody @Valid BookRequestDTO book) {
        Book newBook = bookService.addBook(book);
        if (newBook == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(newBook);

    }

    @PutMapping("{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book) {
        Book updatedBook = bookService.updateBook(id, book);
        if (updatedBook == null) {
           return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(updatedBook);

    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {

        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();

    }
}
