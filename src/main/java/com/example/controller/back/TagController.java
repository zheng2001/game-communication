package com.example.controller.back;

import com.example.common.Result;
import com.example.controller.back.request.TagPageRequest;
import com.example.pojo.Tag;
import com.example.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/back/tag")
public class TagController {
    @Autowired
    TagService tagService;

    @GetMapping("/list")
    public Result list(TagPageRequest tagPageRequest){
        return Result.success(tagService.list(tagPageRequest));
    }

    @PostMapping("/add")
    public Result add(@RequestBody Tag tag){
        boolean result = tagService.addTag(tag);
        if (result){
            return Result.success();
        }else{
            return Result.error("添加失败");
        }
    }

    @GetMapping("/checkTagName/{tagName}")
    public Result checkTagName(@PathVariable String tagName){
        boolean result = tagService.checkTagName(tagName);
        if (result){
            return Result.success("标签名重复");
        }else{
            return Result.error("标签名可用");
        }
    }

    @GetMapping("/checkTagNameWithOutId/{tagName}/{id}")
    public Result checkTagNameWithOutId(@PathVariable String tagName,@PathVariable int id){
        boolean result = tagService.checkTagNameWithOutId(tagName,id);
        if (result){
            return Result.success("标签名重复");
        }else{
            return Result.error("标签名可用");
        }
    }

    @GetMapping("/batchDel/{ids}")
    public Result batchDel(@PathVariable Integer[] ids){
        boolean result = tagService.batchDelByIds(ids);
        if (result){
            return Result.success();
        }else{
            return Result.error("删除失败");
        }
    }

    @GetMapping("/getTagById/{id}")
    public Result getTagById(@PathVariable Integer id){
        Tag tag = tagService.getTagById(id);
        return Result.success(tag);
    }

    @GetMapping("/toUpTag/{id}")
    public Result toUpTag(@PathVariable Integer id){
        boolean result = tagService.toUpTag(id);
        if (result){
            return Result.success();
        }else{
            return Result.error("上移失败");
        }
    }

    @GetMapping("/toDownTag/{id}")
    public Result toDownTag(@PathVariable Integer id){
        boolean result =  tagService.toDownTag(id);
        if (result){
            return Result.success();
        }else{
            return Result.error("下移失败");
        }
    }

    @PostMapping("/update")
    public Result update(@RequestBody Tag tag){
        boolean result = tagService.update(tag);
        if (result){
            return Result.success();
        }else{
            return Result.error("修改失败");
        }
    }

    @GetMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        boolean result = tagService.deleteById(id);
        if (result){
            return Result.success();
        }else{
            return Result.error("删除失败");
        }
    }
}
