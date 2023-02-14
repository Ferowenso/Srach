package com.example.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    private Long postCounter;

    @NotNull
    @Column(unique=true)
    private String codeName;

    @NotNull
    private String name;

    @NotNull
    private Subject subject;

}
