package com.varun.workforce_management.exception;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handles validation errors (e.g., @NotNull, @Size) triggered by @Valid
     * annotations in Controllers.
     * Overrides the default Spring Boot behavior to return a structured
     * ProblemDetail response (RFC 7807).
     * <p>
     * Status: 400 Bad Request
     * Body: Contains a "errors" property with a list of field-specific validation
     * messages.
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        // Collect all validation errors from the exception
        List<ValidationErrorResponse> errors = new ArrayList<>();

        for (FieldError err : ex.getBindingResult().getFieldErrors()) {
            errors.add(new ValidationErrorResponse(
                    err.getField(), // The field that failed validation (e.g., "email")
                    err.getDefaultMessage(), // The error message (e.g., "must not be null")
                    err.getCode())); // The validation code (e.g., "NotNull")
        }

        // Create a Value Object (ProblemDetail) mandated by Spring 6 / Boot 3 for
        // standard error responses
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Validation Failed");
        problemDetail.setTitle("Validation Error");

        // Add the custom list of errors as an extension property
        problemDetail.setProperty("errors", errors);

        return ResponseEntity.badRequest().body(problemDetail);
    }

    @ExceptionHandler(UserExistsException.class)
    public ProblemDetail handleUserExistsException(UserExistsException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problemDetail.setTitle("User already exists");
        return problemDetail;
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ProblemDetail handleBadCredentialsException(BadCredentialsException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, ex.getMessage());
        problemDetail.setTitle("Authentication Failed");
        return problemDetail;
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ProblemDetail expiredJwtException(ExpiredJwtException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, ex.getMessage());
        problemDetail.setTitle("JWT token failed");
        return problemDetail;
    }

    @ExceptionHandler(InvalidJwtTokenException.class)
    public ProblemDetail handleInvalidJwtTokenException(InvalidJwtTokenException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, ex.getMessage());
        problemDetail.setTitle("Invalid JWT Token");
        return problemDetail;
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ProblemDetail handleUserNameNotFoundException(UsernameNotFoundException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setTitle("not found");
        return problemDetail;
    }

    @ExceptionHandler(TokenRefreshException.class)
    public ProblemDetail handleTokenRefreshException(TokenRefreshException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, ex.getMessage());
        problemDetail.setTitle("Token Refresh Failed");
        return problemDetail;
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ProblemDetail handleUnauthorizedAccessException(UnauthorizedAccessException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, ex.getMessage());
        problemDetail.setTitle("Access Denied");
        return problemDetail;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail handleResourceNotFoundException(ResourceNotFoundException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setTitle("Resource Not Found");
        return problemDetail;
    }

    @ExceptionHandler(jakarta.validation.ConstraintViolationException.class)
    public ProblemDetail handleConstraintViolationException(jakarta.validation.ConstraintViolationException ex) {
        List<ValidationErrorResponse> errors = new ArrayList<>();

        for (jakarta.validation.ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(new ValidationErrorResponse(
                    violation.getPropertyPath().toString(),
                    violation.getMessage(),
                    "ConstraintViolation"));
        }

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Validation Failed");
        problemDetail.setTitle("Validation Error");
        problemDetail.setProperty("errors", errors);

        return problemDetail;
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ProblemDetail handleAccessDeniedException(AccessDeniedException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN,
                "You do not have permission to access this resource");
        problemDetail.setTitle("Access Denied");
        return problemDetail;
    }
}
