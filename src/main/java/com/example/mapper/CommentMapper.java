package com.example.mapper;

import com.example.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommentMapper {
    // 添加评论
    boolean add(Comment comment);
    // 获取当前文章id下所有评论
    List<Comment> getCommentsByEid(int eid);
    // 批量删除评论
    boolean batchDelByIds(List<Integer> ids);

}
