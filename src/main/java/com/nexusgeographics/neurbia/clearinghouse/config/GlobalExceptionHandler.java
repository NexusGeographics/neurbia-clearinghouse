package com.nexusgeographics.neurbia.clearinghouse.config;

import com.nexusgeographics.neurbia.clearinghouse.dto.ApiErrorResponse;
import com.nexusgeographics.neurbia.clearinghouse.service.ClearinghouseEventNotFoundException;
import com.nexusgeographics.neurbia.clearinghouse.service.ClearinghousePersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidation(MethodArgumentNotValidException exception) {
        var error = exception.getBindingResult().getFieldErrors().stream().findFirst().orElse(null);
        var detail = error == null ? "Validation failed" : error.getField() + ": " + error.getDefaultMessage();
        if (error != null) {
            log.warn("clearinghouse_event_validation_failed field={} message={}", error.getField(), error.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(new ApiErrorResponse("Bad Request", detail));
    }

    @ExceptionHandler(ClearinghousePersistenceException.class)
    public ResponseEntity<ApiErrorResponse> handlePersistence(ClearinghousePersistenceException exception) {
        log.error("clearinghouse_event_persistence_failed", exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiErrorResponse("Internal Server Error", "Failed to persist clearinghouse event"));
    }

    @ExceptionHandler(ClearinghouseEventNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNotFound(ClearinghouseEventNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiErrorResponse("Not Found", exception.getMessage()));
    }
}
