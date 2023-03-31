package com.example.controller.front;

import com.example.common.Result;
import com.example.pojo.Comment;
import com.example.pojo.Essay;
import com.example.service.CommentService;
import com.example.service.EssayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/front/essayDetails")
public class EssayDetailsController {
    @Autowired
    CommentService commentService;

    @Autowired
    EssayService essayService;

    @GetMapping("/getEssayDetailsById/{id}")
    public Result getEssayDetailsById(@PathVariable Integer id){
        Essay essay = essayService.getEssayDetailsById(id);
        if (essay!=null){
            return Result.success(essay);
        }else{
            return Result.error("没有该文章");
        }

    }

    @GetMapping("/topThreeEssay/{id}")
    public Result topThreeEssay(@PathVariable int id){
        return Result.success(essayService.getTopThree(id));
    }

    @PostMapping("/addComment")
    public Result addComment(@RequestBody Comment comment){
        boolean result = commentService.add(comment);
        if (result){
            return Result.success();
        }else{
            return Result.error("评论失败");
        }
    }

    @GetMapping("/getCommentsById/{eid}")
    public Result getCommentsById(@PathVariable Integer eid){
        List<Comment> comments = commentService.getCommentsByEid(eid);
        List<Comment> rootComments = comments.stream().filter(comment -> comment.getPid() == null).collect(Collectors.toList());
        for (Comment rootComment :rootComments){
            rootComment.setChildren(comments.stream().filter(
                            comment -> rootComment.getId().equals(comment.getPid())
                    ).collect(Collectors.toList())
            );
        }
        return Result.success(rootComments);
    }
}
