package com.varun.workforce_management.exception;

import lombok.Data;

@Data
public class ValidationErrorResponse {
    private String field;
    private String message;
    private String code;


    public ValidationErrorResponse(String field, String message, String code) {
        this.field = field;
        this.message = message;
        this.code = code;
    }
}
