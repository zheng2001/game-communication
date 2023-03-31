package com.example.controller.front;

import com.example.common.Result;
import com.example.controller.front.request.CommunicateRequest;
import com.example.service.EssayService;
import com.example.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/front/communicate")
public class CommunicateController {
    @Autowired
    TagService tagService;

    @Autowired
    EssayService essayService;

    @GetMapping("/defaultTags")
    public Result defaultTags(){
        return Result.success(tagService.getTagsByOrderLtSix());
    }

    @GetMapping("/tags")
    public Result tags(){
        return Result.success(tagService.getTagsByOrderGtFive());
    }

    @GetMapping("/list")
    public Result list(CommunicateRequest communicateRequest){
        return Result.success(essayService.list(communicateRequest));
    }

}
