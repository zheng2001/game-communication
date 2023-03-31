package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin {
    private Integer id;
    private String username;
    private String pwd;
    private String email;
    private Integer adminStatus;
    private String createTime;
    private String loginTime;

}
