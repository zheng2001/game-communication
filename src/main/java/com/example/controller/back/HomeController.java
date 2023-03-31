package com.example.controller.back;

import com.example.common.Result;
import com.example.service.EssayService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/back/home")
public class HomeController {
    @Autowired
    EssayService essayService;

    @Autowired
    UserService userService;

    @GetMapping("/getInfo")
    public Result getInfo(){
        Map<String,Object> map = new HashMap<>();
        Integer allEssays = essayService.getAllEssayCount(); // 总投稿量
        Integer passwdEssays = essayService.getPassedEssayCount(); // 已通过稿件量
        Integer allUsers = userService.getAllUserCount(); // 总用户量
        map.put("allEssays",allEssays);
        map.put("passedEssays",passwdEssays);
        map.put("allUsers",allUsers);
        return Result.success(map);
    }

    @GetMapping("/lineCharts")
    public Result lineCharts() {
        return Result.success(essayService.getCount());
    }
}
