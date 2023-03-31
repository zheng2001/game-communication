package com.example.controller.front;

import com.example.common.Result;
import com.example.pojo.Essay;
import com.example.service.EssayService;
import com.example.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/front/contribute")
public class ContributeController {
    @Autowired
    EssayService essayService;

    @Autowired
    TagService tagService;

    @PostMapping("/addContribute")
    public Result addContribute(@RequestBody Essay essay){
        boolean result = essayService.contribute(essay);
        if (result){
            return Result.success();
        }else{
            return Result.error("投稿失败");
        }
    }

    @GetMapping("/tags")
    public Result tags(){
        return Result.success(tagService.list());
    }

}
