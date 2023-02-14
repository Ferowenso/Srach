package com.example.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Thread {


    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long threadNumber;

    @NotNull
    private String username;

    @NotNull
    private String name;

    @Column(columnDefinition="TEXT")
    private String text;

    @NotNull
    private String ip;

    private boolean locked;

    @NotNull
    @ManyToOne
    private Board board;

    @NotNull
    private LocalDateTime createdAt;

    @NotNull
    private LocalDateTime lastPostTime;

}
