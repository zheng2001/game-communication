package com.example.controller.back.request;

import lombok.Data;

@Data
public class AdminPageRequest extends BaseRequest{
    private String adminStatus;
    private String username;
}
