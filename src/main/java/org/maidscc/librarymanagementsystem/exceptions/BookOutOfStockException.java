package org.maidscc.librarymanagementsystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class BookOutOfStockException extends RuntimeException {
    public BookOutOfStockException(String message) {
        super(message);
    }
}
