package com.example.service;

import com.example.pojo.Comment;

import java.util.List;

public interface CommentService {
    // 添加评论
    boolean add(Comment comment);
    // 获取当前文章id下所有评论
    List<Comment> getCommentsByEid(int eid);
}
