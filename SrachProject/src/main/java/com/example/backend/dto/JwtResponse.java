package com.example.backend.dto;

import com.example.backend.model.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
public class JwtResponse {

    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private List<String> roles;

}
