package org.maidscc.librarymanagementsystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class BorrowingRecordNotFoundException extends RuntimeException {
    public BorrowingRecordNotFoundException(String message) {
        super(message);
    }
}
