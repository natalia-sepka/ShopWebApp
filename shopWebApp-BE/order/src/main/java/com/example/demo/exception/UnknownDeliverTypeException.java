package com.example.demo.exception;

public class UnknownDeliverTypeException extends RuntimeException{
    public UnknownDeliverTypeException() {
        super();
    }

    public UnknownDeliverTypeException(String message) {
        super(message);
    }

    public UnknownDeliverTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownDeliverTypeException(Throwable cause) {
        super(cause);
    }
}

