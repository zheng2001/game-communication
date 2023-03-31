package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Essay {
    private Integer id;
    private Integer uid;
    private Integer tid;
    private String title;
    private String content;
    private Integer essayStatus;
    private String createTime;
    private String releaseTime;
    private String reason;

    private User user;
    private Tag tag;
}
