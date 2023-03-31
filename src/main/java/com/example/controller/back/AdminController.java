package com.example.controller.back;

import com.alibaba.fastjson.JSON;
import com.example.common.Result;
import com.example.controller.back.dto.LoginDTO;
import com.example.controller.back.request.AdminPageRequest;
import com.example.controller.back.request.LoginRequest;
import com.example.pojo.Admin;
import com.example.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/back/admin")
public class AdminController {
    @Autowired
    AdminService adminService;

    @GetMapping("/list")
    public Result list(AdminPageRequest adminPageRequest){
        return Result.success(adminService.list(adminPageRequest));
    }

    @PostMapping("/add")
    public Result add(@RequestBody Map<String,Object> param){
        param.remove("checkPwd");
        Admin admin = JSON.parseObject(JSON.toJSONString(param),Admin.class);
        boolean result = adminService.addAdmin(admin);
        if (result){
            return Result.success();
        }else{
            return Result.error("添加失败");
        }
    }

    @GetMapping("/checkUsername/{username}")
    public Result checkUsername(@PathVariable String username){
        boolean result = adminService.checkUsername(username);
        if (result){
            return Result.success("用户名重复");
        }else{
            return Result.error("用户名可用");
        }
    }

    @GetMapping("/getAdminById/{id}")
    public Result getAdminById(@PathVariable Integer id){
        Admin admin = adminService.getAdminById(id);
        return Result.success(admin);
    }

    @PostMapping("/update")
    public Result update(@RequestBody Map<String,Object> param){
        Admin admin = JSON.parseObject(JSON.toJSONString(param.get("admin")),Admin.class);
        Boolean isChange = (Boolean) param.get("isChange");
        boolean result = adminService.update(admin, isChange);
        if (result){
            return Result.success();
        }else{
            return Result.error("修改失败");
        }
    }

    @GetMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        boolean result = adminService.deleteById(id);
        if (result){
            return Result.success();
        }else{
            return Result.error("删除失败");
        }
    }

    @PostMapping("/login")
    public Result login(@RequestBody LoginRequest loginRequest){
        LoginDTO loginDTO = adminService.login(loginRequest);
        return Result.success(loginDTO);
    }
}
