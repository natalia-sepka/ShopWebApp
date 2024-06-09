package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class UserRegisterDTO {
    private String login;
    private String email;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String password;
    private String role;
}
