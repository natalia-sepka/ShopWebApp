package com.example.auth.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ChangePasswordData {
    private String uid;
    private String password;
}
