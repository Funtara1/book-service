package com.funtara.book_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleBookNotFound(BookNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(base(HttpStatus.NOT_FOUND, "Book Not Found", ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
        var errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                .toList();
        return ResponseEntity.badRequest().body(base(HttpStatus.BAD_REQUEST, "Validation Failed", errors));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleOtherExceptions(Exception ex) {
        return ResponseEntity.internalServerError().body(base(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", ex.getMessage()));
    }

    private Map<String, Object> base(HttpStatus status, String error, Object message) {
        return Map.of(
                "timestamp", LocalDateTime.now(),
                "status", status.value(),
                "error", error,
                "message", message
        );
    }
}
