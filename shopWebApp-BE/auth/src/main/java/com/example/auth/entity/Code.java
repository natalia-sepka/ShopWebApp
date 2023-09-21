package com.example.auth.entity;

public enum Code {
    SUCCESS("Operation end with success"),
    PERMIT("Access granted"),
    A1("Failed to login"),
    A2("No such user"),
    A3("Empty or expired token"),
    A4("Username already taken"),
    A5("Email already taken");


    public final String label;
     Code(String label) {
        this.label = label;
    }
}
