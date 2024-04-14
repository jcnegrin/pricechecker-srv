package com.pricecheker.project.infrastructure.configuration;

import com.pricecheker.project.domain.exception.GenericRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception e) {
        return ResponseEntity.status(500).body(e.getMessage());
    }

    @ExceptionHandler(GenericRuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntimeException(GenericRuntimeException e) {
        return ResponseEntity.status(e.getErrorCode()).body(Map.of(
                "errorCode", e.getErrorCode().value(),
                "description", e.getDescription(),
                "detail", e.getDetail()
        ));
    }
}
