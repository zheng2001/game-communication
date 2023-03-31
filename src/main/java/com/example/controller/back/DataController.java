package com.example.controller.back;

import com.example.common.Result;
import com.example.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/back/data")
public class DataController {
    @Autowired
    DataService dataService;

    @GetMapping("/getDataByKey")
    public Result getDataByKey(String key){
        return Result.success(dataService.getDataByKey(key));
    }
}
