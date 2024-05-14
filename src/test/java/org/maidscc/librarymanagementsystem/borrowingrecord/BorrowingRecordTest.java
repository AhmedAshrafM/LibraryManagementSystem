package org.maidscc.librarymanagementsystem.borrowingrecord;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.maidscc.librarymanagementsystem.exceptions.BookNotFoundException;
import org.maidscc.librarymanagementsystem.exceptions.BorrowingRecordNotFoundException;
import org.maidscc.librarymanagementsystem.models.Book;
import org.maidscc.librarymanagementsystem.models.BorrowingRecord;
import org.maidscc.librarymanagementsystem.models.Patron;
import org.maidscc.librarymanagementsystem.repositories.BookRepository;
import org.maidscc.librarymanagementsystem.repositories.BorrowingRecordRepository;
import org.maidscc.librarymanagementsystem.repositories.PatronRepository;
import org.maidscc.librarymanagementsystem.services.BorrowingRecordService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BorrowingRecordTest {

    @Mock
    private BorrowingRecordRepository borrowingRecordRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private PatronRepository patronRepository;

    @InjectMocks
    private BorrowingRecordService borrowingRecordService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this); // Initialize the annotated fields
    }

    @Test
    public void testBorrowBook_Success() {
        // Arrange
        long patronId = 1L;
        long bookId = 1L;
        LocalDate returnDate = LocalDate.now().plusDays(7);
        Book book = new Book();
        book.setId(bookId);
        book.setStock(1);
        Patron patron = new Patron();
        patron.setId(patronId);
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(patronRepository.findById(patronId)).thenReturn(Optional.of(patron));
        when(borrowingRecordRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        BorrowingRecord borrowingRecord = borrowingRecordService.borrowBook(patronId, bookId, returnDate);

        // Assert
        assertNotNull(borrowingRecord);
        assertEquals(book, borrowingRecord.getBook());
        assertEquals(patron, borrowingRecord.getPatron());
        assertEquals(LocalDate.now(), borrowingRecord.getBorrowDate());
        assertEquals(returnDate, borrowingRecord.getReturnDate());
        assertFalse(borrowingRecord.isReturned());
        verify(bookRepository, times(1)).findById(bookId);
        verify(patronRepository, times(1)).findById(patronId);
        verify(borrowingRecordRepository, times(1)).save(any());
    }

    @Test
    public void testBorrowBook_BookNotFound() {
        // Arrange
        long patronId = 1L;
        long bookId = 1L;
        LocalDate returnDate = LocalDate.now().plusDays(7);
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(BookNotFoundException.class, () -> borrowingRecordService.borrowBook(patronId, bookId, returnDate));
        verify(bookRepository, times(1)).findById(bookId);
        verify(patronRepository, never()).findById(anyLong());
        verify(borrowingRecordRepository, never()).save(any());
    }

    @Test
    public void testReturnBook_Success() {
        // Arrange
        long patronId = 1L;
        long bookId = 1L;
        List<BorrowingRecord> borrows = new ArrayList<>();
        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setId(1L);
        borrowingRecord.setBook(new Book());
        borrowingRecord.setPatron(new Patron());
        borrowingRecord.setReturned(false);
        borrows.add(borrowingRecord);
        when(borrowingRecordRepository.findByBookIdAndPatronId(bookId, patronId)).thenReturn(borrows);
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(new Book()));

        // Act
        boolean result = borrowingRecordService.returnBook(patronId, bookId);

        // Assert
        assertTrue(result);
        assertTrue(borrowingRecord.isReturned());
        verify(borrowingRecordRepository, times(1)).findByBookIdAndPatronId(bookId, patronId);
        verify(bookRepository, times(1)).findById(bookId);
        verify(borrowingRecordRepository, times(1)).save(borrowingRecord);
    }

    @Test
    public void testReturnBook_NoRecordFound() {
        // Arrange
        long patronId = 1L;
        long bookId = 1L;
        when(borrowingRecordRepository.findByBookIdAndPatronId(bookId, patronId)).thenReturn(new ArrayList<>());

        // Act + Assert
        assertThrows(BorrowingRecordNotFoundException.class, () -> borrowingRecordService.returnBook(patronId, bookId));
        verify(borrowingRecordRepository, times(1)).findByBookIdAndPatronId(bookId, patronId);
        verify(bookRepository, never()).findById(anyLong());
        verify(borrowingRecordRepository, never()).save(any());
    }

}

