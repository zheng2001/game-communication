package com.example.controller.back.dto;

import lombok.Data;

@Data
public class LoginDTO {
    private Integer id;
    private String username;
    private String email;
    private String token;
}
