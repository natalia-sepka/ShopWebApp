package com.example.demo.exception;

public class OrderDoesntExistException extends RuntimeException{
    public OrderDoesntExistException() {
        super();
    }

    public OrderDoesntExistException(String message) {
        super(message);
    }

    public OrderDoesntExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderDoesntExistException(Throwable cause) {
        super(cause);
    }
}

