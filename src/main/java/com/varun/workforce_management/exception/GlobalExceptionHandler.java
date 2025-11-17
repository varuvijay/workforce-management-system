package com.varun.workforce_management.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        // use streams for less code
        List<ValidationErrorResponse> errors = new ArrayList<>();

        for (FieldError err : ex.getBindingResult().getFieldErrors()) {
            errors.add(new ValidationErrorResponse(
                    err.getField(),
                    err.getDefaultMessage(),
                    err.getCode()
            ));
        }
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(UserExistsException.class)
    public ResponseEntity<Map<String,String>> handleUserExistsException(UserExistsException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message", ex.getMessage()));
    }
}
