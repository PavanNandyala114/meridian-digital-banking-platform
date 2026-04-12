package com.meridian.customerservice.exception;

import com.meridian.customerservice.payload.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;


@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ApiResponse.<String>builder()
                .success(false)
                .message(ex.getMessage())
                .data(null)
                .build();
    }


    @ExceptionHandler(DuplicateResourceException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiResponse<String> handleDuplicateResourceException(DuplicateResourceException ex) {
        return ApiResponse.<String>builder()
                .success(false)
                .message(ex.getMessage())
                .data(null)
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<String> handleValidationException(MethodArgumentNotValidException ex) {
        String errorMessage  = ex.getBindingResult().getFieldErrors()
                .stream()
                .findFirst()
                .map(error -> error.getField() + ":" +error.getDefaultMessage())
                .orElse("Validation Failed");
            return ApiResponse.<String>builder()
                    .success(false)
                    .message(errorMessage)
                    .data(null)
                    .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<String> handleGenericException(Exception ex) {

        return ApiResponse.<String>builder()
                .success(false)
                .message(ex.getMessage())
                .data(null)
                .build();

    }

}
