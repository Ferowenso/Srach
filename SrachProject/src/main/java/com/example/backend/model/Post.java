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
public class Post {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long postNumber;

    @NotNull
    @Column(columnDefinition="TEXT")
    private String text;

    @NotNull
    private String username;

    @NotNull
    private boolean sage;

    @NotNull
    private boolean op;

    @NotNull
    private String ip;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "thread_id")
    private Thread thread;

    @NotNull
    private LocalDateTime dateTime;


}
