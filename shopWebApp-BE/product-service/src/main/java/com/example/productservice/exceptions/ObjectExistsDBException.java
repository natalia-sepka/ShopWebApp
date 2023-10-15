package com.example.productservice.exceptions;

public class ObjectExistsDBException extends RuntimeException {
    public ObjectExistsDBException(String message) {
        super(message);
    }

    public ObjectExistsDBException(String message, Throwable cause) {
        super(message, cause);
    }

    public ObjectExistsDBException(Throwable cause) {
        super(cause);
    }
}
