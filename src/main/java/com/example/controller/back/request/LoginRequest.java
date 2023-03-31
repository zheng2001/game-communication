package com.example.controller.back.request;

import lombok.Data;

@Data
public class LoginRequest extends BaseRequest{
    private String username;
    private String pwd;

}
