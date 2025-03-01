package com.inditex.infrastructure.exception;

import com.inditex.domain.exception.NotFoundException;
import com.inditex.domain.model.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFound(NotFoundException ex, WebRequest request) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgument(IllegalArgumentException ex, WebRequest request) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneralException(Exception ex, WebRequest request) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred.");
    }


    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Object> handleMissingParams(MissingServletRequestParameterException ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST,
                String.format("The required query parameter '%s' is missing.", ex.getParameterName()));
    }
    private ResponseEntity<Object> buildErrorResponse(HttpStatus status, String message) {
        var body = new ErrorResponseDTO();
        body.setTimestamp(LocalDateTime.now());
        body.setStatus(status.value());
        body.setError(status.getReasonPhrase());
        body.setMessage(message);
        return new ResponseEntity<>(body, status);
    }
}
