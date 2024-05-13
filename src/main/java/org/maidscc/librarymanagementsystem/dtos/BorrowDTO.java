package org.maidscc.librarymanagementsystem.dtos;

import java.time.LocalDate;

public record BorrowDTO(
        Long bookId,
        Long patronId,
        LocalDate returnDate
) {
}
