package com.example.backend.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
public class LoginRequest {

    @NotNull
    private String username;
    @NotNull
    private String password;

}
