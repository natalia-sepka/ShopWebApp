package com.example.demo.exception;

public class UserDoesntLogInException extends RuntimeException{
    public UserDoesntLogInException() {
        super();
    }

    public UserDoesntLogInException(String message) {
        super(message);
    }

    public UserDoesntLogInException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserDoesntLogInException(Throwable cause) {
        super(cause);
    }
}

