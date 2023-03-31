package com.example.controller.back.request;

import lombok.Data;

@Data
public class UserPageRequest extends BaseRequest{
    private String name; //昵称或者用户名
    private String userStatus;

}
