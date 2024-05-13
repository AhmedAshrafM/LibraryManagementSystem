package org.maidscc.librarymanagementsystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class DuplicateFoundException extends RuntimeException{
    public DuplicateFoundException(String message) {
        super(message);
    }
}
