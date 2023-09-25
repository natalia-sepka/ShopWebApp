package com.example.auth.entity;

public enum Code {
    SUCCESS("Operation end with success"),
    PERMIT("Access granted"),
    A1("User doesn't exist or didn't activate account by mail"),
    A2("Invalid data"),
    A3("Empty or expired token"),
    A4("Username already taken"),
    A5("Email already taken"),
    A6("User doesn't exist");


    public final String label;
     Code(String label) {
        this.label = label;
    }
}
