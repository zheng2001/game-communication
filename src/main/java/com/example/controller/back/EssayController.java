package com.example.controller.back;

import com.example.common.Result;
import com.example.controller.back.request.BaseRequest;
import com.example.pojo.Essay;
import com.example.service.EssayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/back/essay")
public class EssayController {
    @Autowired
    EssayService essayService;

    @GetMapping("/list")
    public Result list(BaseRequest BaseRequest){
        return Result.success(essayService.listByPage(BaseRequest));
    }

    @GetMapping("/getEssayById/{id}")
    public Result getEssayById(@PathVariable Integer id){
        Essay essay = essayService.getEssayById(id);
        return Result.success(essay);
    }

    @PostMapping("/update")
    public Result update(@RequestBody Essay essay){
        boolean result = essayService.update(essay);
        if (result){
            return Result.success();
        }else{
            return Result.error("修改失败");
        }
    }
}
