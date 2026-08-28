package com.mindata.hotelsearch.infraestructure.adapter.in.exception;

import com.mindata.hotelsearch.domain.exception.SearchNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GlobalExceptionHandlerTest {
    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void shouldHandleDomainExceptions() {
        ResponseEntity<?> notFound = handler.handleSearchNotFound(new SearchNotFoundException("abc"));
        ResponseEntity<?> badRequest = handler.handleIllegalArgument(new IllegalArgumentException("invalid"));

        assertAll(
                () -> assertEquals(HttpStatus.NOT_FOUND, notFound.getStatusCode()),
                () -> assertEquals(HttpStatus.BAD_REQUEST, badRequest.getStatusCode())
        );
    }
}
