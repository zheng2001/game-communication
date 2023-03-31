package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    private Integer id;
    private Integer uid;
    private String content;
    private String createTime;
    private Integer eid;
    private Integer pid;
    private Integer target;

    private User user; // 一个评论对应一个用户
    private List<Comment> children;
}
