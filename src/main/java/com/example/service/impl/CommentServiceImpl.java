package com.example.service.impl;

import com.example.mapper.CommentMapper;
import com.example.pojo.Comment;
import com.example.service.CommentService;
import com.example.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentMapper commentMapper;

    @Override
    public boolean add(Comment comment) {
        comment.setCreateTime(CommonUtil.getNow());
        return commentMapper.add(comment);
    }

    @Override
    public List<Comment> getCommentsByEid(int eid) {
        return commentMapper.getCommentsByEid(eid);
    }
}
