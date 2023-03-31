package com.example.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer id;
    private String nickname;
    private String username;
    private String pwd;
    private Integer age;
    private String sex;
    private String headImg;
    private Integer userStatus;
    private String createTime;
    private String loginTime;
}
