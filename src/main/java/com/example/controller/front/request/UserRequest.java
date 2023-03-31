package com.example.controller.front.request;

import lombok.Data;

@Data
public class UserRequest {
    private Integer pageNum = 1;
    private Integer pageSize = 2;
    private Integer uid;
}
