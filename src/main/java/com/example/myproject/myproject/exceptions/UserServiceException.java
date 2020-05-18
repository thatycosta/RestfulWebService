package com.example.myproject.myproject.exceptions;

public class UserServiceException extends RuntimeException{

    private static final long serialVersionUID = 12312156445134L;

    public UserServiceException(String message) {
        super(message);
    }
}
