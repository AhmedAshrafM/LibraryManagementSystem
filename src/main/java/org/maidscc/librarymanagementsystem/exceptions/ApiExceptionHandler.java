package org.maidscc.librarymanagementsystem.exceptions;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.maidscc.librarymanagementsystem.dtos.ApiErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ApiErrorDto> handleBookNotFoundException(BookNotFoundException ex) {
        var errorDetails = new ApiErrorDto(new HashMap<String, Object>(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
    }

    @ExceptionHandler(PatronNotFoundException.class)
    public ResponseEntity<ApiErrorDto> handlePatronNotFoundException(PatronNotFoundException ex) {
        var errorDetails = new ApiErrorDto(new HashMap<String, Object>(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
    }

    @ExceptionHandler(BorrowingRecordNotFoundException.class)
    public ResponseEntity<ApiErrorDto> handleBorrowingRecordNotFoundException(BorrowingRecordNotFoundException ex) {
        var errorDetails = new ApiErrorDto(new HashMap<String, Object>(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
    }

    @ExceptionHandler(BookAlreadyReturnedException.class)
    public ResponseEntity<ApiErrorDto> handleBookAlreadyReturnedException(BookAlreadyReturnedException ex) {
        var errorDetails = new ApiErrorDto(new HashMap<String, Object>(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
    }

    @ExceptionHandler(BookOutOfStockException.class)
    public ResponseEntity<ApiErrorDto> handleBookOutOfStockException(BookOutOfStockException ex) {
        var errorDetails = new ApiErrorDto(new HashMap<String, Object>(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
    }

    @ExceptionHandler(DuplicateFoundException.class)
    public ResponseEntity<ApiErrorDto> handleDuplicateFoundException(DuplicateFoundException ex) {
        var errorDetails = new ApiErrorDto(new HashMap<String, Object>(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorDetails);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorDto> IllegalArgumentException(IllegalArgumentException ex) {
        var errorDetails = new ApiErrorDto(new HashMap<String, Object>(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiErrorDto> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, String> constraintViolations = new HashMap<>();
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations) {
            constraintViolations.put(violation.getPropertyPath().toString(), violation.getMessage());
        }
        ApiErrorDto errorDetails = new ApiErrorDto(constraintViolations, "Constraint violations occurred");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
    }

}
