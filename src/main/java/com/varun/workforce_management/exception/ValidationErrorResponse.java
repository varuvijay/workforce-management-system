package com.varun.workforce_management.exception;

public record ValidationErrorResponse(
    String field,
    String message,
    String code) {}
