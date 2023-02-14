package com.example.backend.dto;

import com.example.backend.model.Subject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
public class BoardDto {

    @NotNull
    private String codeName;

    @NotNull
    private String name;

    @NotNull
    private Subject subject;

}
