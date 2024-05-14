package org.maidscc.librarymanagementsystem.book;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.maidscc.librarymanagementsystem.exceptions.BookNotFoundException;
import org.maidscc.librarymanagementsystem.exceptions.DuplicateFoundException;
import org.maidscc.librarymanagementsystem.models.Book;
import org.maidscc.librarymanagementsystem.repositories.BookRepository;
import org.maidscc.librarymanagementsystem.services.BookService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this); // Initialize the annotated fields
    }

    @Test
    public void testGetAllBooks() {
        // Arrange
        Book book1 = new Book();
        book1.setId(1L);
        Book book2 = new Book();
        book2.setId(2L);
        when(bookRepository.findAll()).thenReturn(List.of(book1, book2));

        // Act
        List<Book> books = bookService.getAllBooks();

        // Assert
        assertEquals(2, books.size());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    public void testGetBookById() {
        // Arrange
        Book book = new Book();
        book.setId(1L);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        // Act
        Book returnedBook = bookService.getBookById(1L);

        // Assert
        assertEquals(book.getId(), returnedBook.getId());
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetBookById_InvalidId() {
        // Arrange
        long invalidId = -1L;
        when(bookRepository.findById(invalidId)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(BookNotFoundException.class, () -> {
            bookService.getBookById(invalidId);
        });
        verify(bookRepository, times(1)).findById(invalidId);
    }

    @Test
    public void testAddBook() {
        // Arrange
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Some Book Title");
        book.setIsbn("1-7-2-1-5");
        book.setAuthor("Some Book Author");
        book.setPublisher("Some Publisher");
        book.setPublicationYear("2003");
        book.setGenre("Some genre");
        book.setStock(10);

        when(bookRepository.save(book)).thenReturn(book);
        // Act
        Book returnedBook = bookService.addBook(book);

        // Assert
        assertEquals(book.getId(), returnedBook.getId());
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    public void testAddBook_DuplicateTitle() {
        // Arrange
        Book book = new Book();
        book.setTitle("Duplicate Book");

        when(bookRepository.findByTitle(book.getTitle())).thenReturn(Optional.of(new Book()));

        // Act + Assert
        assertThrows(DuplicateFoundException.class, () -> {
            bookService.addBook(book);
        });
        verify(bookRepository, never()).save(any(Book.class)); // Ensure the book is not saved
    }

    @Test
    public void testUpdateBook() {
        // Arrange
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Book Title");
        book.setAuthor("Book Author");

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.save(book)).thenReturn(book);

        // Act
        Book returnedBook = bookService.updateBook(1L, book);

        // Assert
        assertEquals(book.getId(), returnedBook.getId());
        verify(bookRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    public void testUpdateBook_BookNotFound() {
        // Arrange
        long nonExistingId = 100L;
        Book bookToUpdate = new Book();
        bookToUpdate.setId(nonExistingId);

        when(bookRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(BookNotFoundException.class, () -> {
            bookService.updateBook(nonExistingId, bookToUpdate);
        });
        verify(bookRepository, never()).save(any(Book.class)); // Ensure the book is not saved
    }

    @Test
    public void testDeleteBook() {
        // Arrange
        when(bookRepository.findById(1L)).thenReturn(Optional.of(new Book()));

        // Act
        bookService.deleteBook(1L);

        // Assert
        verify(bookRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteBook_BookNotFound() {
        // Arrange
        long nonExistingId = 100L;
        when(bookRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(BookNotFoundException.class, () -> {
            bookService.deleteBook(nonExistingId);
        });
        verify(bookRepository, never()).deleteById(nonExistingId); // Ensure the book is not deleted
    }


}

