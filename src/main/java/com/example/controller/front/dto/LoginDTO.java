package com.example.controller.front.dto;

import lombok.Data;

@Data
public class LoginDTO {
    private Integer id;
    private String nickname;
    private String username;
    private String headImg;
    private String token;
}
