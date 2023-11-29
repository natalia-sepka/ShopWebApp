package com.example.basket.exceptions;

public class BasketItemDoesntExistException extends RuntimeException {
    public BasketItemDoesntExistException() {
        super();
    }

    public BasketItemDoesntExistException(String message) {
        super(message);
    }

    public BasketItemDoesntExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public BasketItemDoesntExistException(Throwable cause) {
        super(cause);
    }
}
