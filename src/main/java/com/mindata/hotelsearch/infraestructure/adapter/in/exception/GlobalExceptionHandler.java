package com.mindata.hotelsearch.infraestructure.adapter.in.exception;

import com.mindata.hotelsearch.domain.exception.SearchNotFoundException;
import com.mindata.hotelsearch.infraestructure.adapter.in.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationException(MethodArgumentNotValidException exception) {
        String message = exception.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(FieldError::getDefaultMessage)
                .orElse("Invalid request payload");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDto(message));
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ErrorResponseDto> handleHandlerMethodValidation(HandlerMethodValidationException exception) {
        String message = exception.getAllErrors().stream()
                .map(error -> error.getDefaultMessage() == null ? "Invalid request parameter" : error.getDefaultMessage())
                .findFirst()
                .orElse("Invalid request parameter");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDto(message));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDto> handleIllegalArgument(IllegalArgumentException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDto(exception.getMessage()));
    }

    @ExceptionHandler(SearchNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleSearchNotFound(SearchNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDto(exception.getMessage()));
    }
}
