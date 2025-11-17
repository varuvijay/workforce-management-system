package com.varun.workforce_management.exception;


public class UserExistsException extends RuntimeException{
    public UserExistsException(String message) {
        super(message);
    }

}
