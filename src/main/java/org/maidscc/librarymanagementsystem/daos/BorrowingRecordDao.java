package org.maidscc.librarymanagementsystem.daos;

import org.maidscc.librarymanagementsystem.models.BorrowingRecord;

import java.time.LocalDate;

public interface BorrowingRecordDao {
    BorrowingRecord borrowBook(Long patronId, Long bookId, LocalDate returnDate);

    boolean returnBook(Long patronId, Long bookId);
}
