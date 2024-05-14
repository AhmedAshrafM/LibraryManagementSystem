package org.maidscc.librarymanagementsystem.services;

import org.maidscc.librarymanagementsystem.daos.BorrowingRecordDao;
import org.maidscc.librarymanagementsystem.exceptions.*;
import org.maidscc.librarymanagementsystem.models.Book;
import org.maidscc.librarymanagementsystem.models.BorrowingRecord;
import org.maidscc.librarymanagementsystem.models.Patron;
import org.maidscc.librarymanagementsystem.repositories.BookRepository;
import org.maidscc.librarymanagementsystem.repositories.BorrowingRecordRepository;
import org.maidscc.librarymanagementsystem.repositories.PatronRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class BorrowingRecordService implements BorrowingRecordDao {
    private final BorrowingRecordRepository borrowingRecordRepository;
    private final BookRepository bookRepository;
    private final PatronRepository patronRepository;

    public BorrowingRecordService(BorrowingRecordRepository borrowingRecordRepository, BookRepository bookRepository, PatronRepository patronRepository) {

        this.borrowingRecordRepository = borrowingRecordRepository;
        this.bookRepository = bookRepository;
        this.patronRepository = patronRepository;
    }

    @Transactional
    @Override
    public BorrowingRecord borrowBook(Long patronId, Long bookId, LocalDate returnDate) {
        try {
            Book book = bookRepository.findById(bookId).orElseThrow(() ->
                    new BookNotFoundException("book with id [%s] not found".formatted(bookId)));
            if (book.getStock() < 1) {
                throw new BookOutOfStockException("Book with id " + bookId + " is currently out of stock");
            }
            Patron patron = patronRepository.findById(patronId)
                    .orElseThrow(() -> new PatronNotFoundException("patron with id [%s} not found".formatted(patronId)));
            if (returnDate.isBefore(LocalDate.now())) {
                throw new IllegalArgumentException("Return date can't be before the current date");
            }
            if (returnDate.isAfter(LocalDate.now().plusDays(7))) {
                throw new IllegalArgumentException("Borrowing time can't be more than 7 days");
            }
            BorrowingRecord borrow = new BorrowingRecord();
            borrow.setBook(book);
            borrow.setPatron(patron);
            borrow.setBorrowDate(LocalDate.now());
            borrow.setReturnDate(returnDate);
            borrow.setReturned(false);
            book.decrementStock();


            return borrowingRecordRepository.save(borrow);
        } catch (BookNotFoundException e) {
            throw new BookNotFoundException(e.getMessage());
        } catch (PatronNotFoundException e) {
            throw new PatronNotFoundException(e.getMessage());
        }
    }

    @Transactional
    @Override
    public boolean returnBook(Long patronId, Long bookId) {
        List<BorrowingRecord> borrows = borrowingRecordRepository.findByBookIdAndPatronId(bookId, patronId);

        if (borrows.isEmpty()) {
            throw new BorrowingRecordNotFoundException("No record found with the specified ids");
        }

        boolean foundReturned = false; // Flag to track if a returned record is found
        boolean foundNotReturned = false; // Flag to track if a not-returned record is found

        for (BorrowingRecord borrow : borrows) {
            if (borrow.isReturned()) {
                foundReturned = true; // Mark that a returned record is found
            } else {
                foundNotReturned = true; // Mark that a not-returned record is found
                borrow.setReturned(true);
                Book book = bookRepository.findById(bookId).orElseThrow(() ->
                        new BookNotFoundException("Book with id [%s] not found".formatted(bookId)));
                book.incrementStock();
                borrowingRecordRepository.save(borrow);
            }
        }
        if (foundReturned && !foundNotReturned) {
            throw new BookAlreadyReturnedException("Book already returned by the patron");
        }
        return true;
    }
}
