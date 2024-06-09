package com.example.demo.exception;

public class BasketDoesntExistException extends RuntimeException{
    public BasketDoesntExistException() {
        super();
    }

    public BasketDoesntExistException(String message) {
        super(message);
    }

    public BasketDoesntExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public BasketDoesntExistException(Throwable cause) {
        super(cause);
    }
}

