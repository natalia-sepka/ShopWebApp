package com.example.fileservice.exceptions;

public class FtpConnException extends RuntimeException {
    public FtpConnException(String message) {
        super(message);
    }

    public FtpConnException(String message, Throwable cause) {
        super(message, cause);
    }

    public FtpConnException(Throwable cause) {
        super(cause);
    }
}
