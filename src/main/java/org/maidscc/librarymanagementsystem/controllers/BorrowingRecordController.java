package org.maidscc.librarymanagementsystem.controllers;

import jakarta.validation.Valid;
import org.maidscc.librarymanagementsystem.dtos.BorrowDTO;
import org.maidscc.librarymanagementsystem.exceptions.BookNotFoundException;
import org.maidscc.librarymanagementsystem.exceptions.BookOutOfStockException;
import org.maidscc.librarymanagementsystem.exceptions.BorrowingRecordNotFoundException;
import org.maidscc.librarymanagementsystem.exceptions.PatronNotFoundException;
import org.maidscc.librarymanagementsystem.models.BorrowingRecord;
import org.maidscc.librarymanagementsystem.services.BorrowingRecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BorrowingRecordController {
    private final BorrowingRecordService borrowingRecordService;

    public BorrowingRecordController(BorrowingRecordService borrowingRecordService) {
        this.borrowingRecordService = borrowingRecordService;
    }

    @PostMapping("/borrow")
    public ResponseEntity<BorrowingRecord> borrowBook(@RequestBody @Valid BorrowDTO borrow) {
        BorrowingRecord borrowRecord = borrowingRecordService.borrowBook(borrow.patronId(), borrow.bookId(), borrow.returnDate());
            return ResponseEntity.ok(borrowRecord);
    }

    @PutMapping("/return/{bookId}/patron/{patronId}")
    public ResponseEntity<Boolean> returnBook(@PathVariable Long bookId, @PathVariable Long patronId) {
       boolean returnBook = borrowingRecordService.returnBook(patronId, bookId);
            return ResponseEntity.ok(returnBook);

    }
}
