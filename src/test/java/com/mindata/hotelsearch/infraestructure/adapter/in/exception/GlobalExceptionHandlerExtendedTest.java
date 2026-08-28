package com.mindata.hotelsearch.infraestructure.adapter.in.exception;

import com.mindata.hotelsearch.infraestructure.adapter.in.dto.ErrorResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerExtendedTest {
    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void shouldHandleMethodArgumentNotValidException() throws NoSuchMethodException {
        Object target = new Object();
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(target, "request");
        bindingResult.addError(new FieldError("request", "hotelId", "hotelId must not be blank"));
        Method method = GlobalExceptionHandlerExtendedTest.class.getDeclaredMethod("sampleMethod", String.class);
        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(
                new MethodParameter(method, 0),
                bindingResult
        );

        ResponseEntity<ErrorResponseDto> response = handler.handleValidationException(exception);

        assertAll(
                () -> assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode()),
                () -> assertNotNull(response.getBody()),
                () -> assertEquals("hotelId must not be blank", response.getBody().message())
        );
    }

    @Test
    void shouldHandleHandlerMethodValidationException() {
        HandlerMethodValidationException exception = mock(HandlerMethodValidationException.class);
        when(exception.getAllErrors()).thenReturn(java.util.List.of());

        ResponseEntity<ErrorResponseDto> response = handler.handleHandlerMethodValidation(exception);

        assertAll(
                () -> assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode()),
                () -> assertEquals("Invalid request parameter", response.getBody().message())
        );
    }

    @SuppressWarnings("unused")
    private void sampleMethod(String value) {
    }
}
