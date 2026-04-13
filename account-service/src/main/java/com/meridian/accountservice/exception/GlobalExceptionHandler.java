package com.meridian.accountservice.exception;

import com.meridian.accountservice.payload.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return new ResponseEntity<>(ApiResponse.<String>builder()
                .success(false)
                .message(ex.getMessage())
                .data(null)
                .build(),
                HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ApiResponse<String>> handleDuplicateResourceException(DuplicateResourceException ex) {
        return new ResponseEntity<>(ApiResponse.<String>builder()
                .success(false)
                .message(ex.getMessage())
                .data(null)
                .build(),
                HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleGenericException(Exception ex) {
        return new ResponseEntity<>(ApiResponse.<String>builder()
                .success(false)
                .message("Internal server error: " + ex.getMessage())
                .data(null)
                .build(),
                HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<String>> handleValidationException(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .reduce((msg1, msg2) -> msg1 + "; " + msg2)
                .orElse("Validation failed");

            return new ResponseEntity<>(ApiResponse.<String>builder()
                    .success(false)
                    .message(errorMessage)
                    .data(null)
                    .build(),
                    HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>(ApiResponse.<String>builder()
                .success(false)
                .message(ex.getMessage())
                .data(null)
                .build(),
                HttpStatus.BAD_REQUEST);
    }


}
