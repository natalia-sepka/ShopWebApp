package com.example.auth.entity;

public enum Code {
    SUCCESS("Operation end with success"),
    PERMIT("Access granted"),
    A1("Failed to login"),
    A2("No such user"),
    A3("Empty or expired token");


    public final String label;
    private Code(String label) {
        this.label = label;
    }
}
