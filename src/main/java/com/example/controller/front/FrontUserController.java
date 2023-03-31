package com.example.controller.front;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.example.common.Result;
import com.example.controller.front.dto.LoginDTO;
import com.example.controller.front.request.LoginRequest;
import com.example.controller.front.request.UserRequest;
import com.example.pojo.User;
import com.example.service.EssayService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/front/user")
public class FrontUserController {
    @Autowired
    UserService userService;

    @Autowired
    EssayService essayService;

    private static final String BASE_FILE_PATH = System.getProperty("user.dir")+"/upload/avatar/";

    @PostMapping("/login")
    public Result login(@RequestBody LoginRequest loginRequest){
        LoginDTO loginDTO = userService.login(loginRequest);
        return Result.success(loginDTO);
    }

    @PostMapping("/register")
    public Result register(@RequestBody Map<String,Object> param){
        param.remove("checkPwd");
        User user = JSON.parseObject(JSON.toJSONString(param), User.class);
        boolean result = userService.register(user);
        if (result){
            return Result.success();
        }else{
            return Result.error("注册失败");
        }
    }

    @GetMapping("/checkUsername/{username}")
    public Result checkUsername(@PathVariable String username){
        boolean result = userService.checkUsername(username);
        if (result){
            return Result.success("用户名重复");
        }else{
            return Result.error("用户名可用");
        }
    }

    @GetMapping("/avatar/download/{flag}")
    public void download(@PathVariable String flag, HttpServletResponse response) {
        OutputStream os;
        List<String> fileNames = FileUtil.listFileNames(BASE_FILE_PATH);
        String fileName = fileNames.stream().filter(name -> name.contains(flag)).findAny().orElse(""); //  System.currentTimeMillis()
        try {
            if (StrUtil.isNotEmpty(fileName)) {
                byte[] bytes = FileUtil.readBytes(BASE_FILE_PATH + fileName);
                os = response.getOutputStream();
                os.write(bytes);
                os.flush();
                os.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/avatar/upload")
    public Result upload(MultipartFile file) {
        //获取文件的名称
        String originalFilename = file.getOriginalFilename();
        if (StrUtil.isBlank(originalFilename)) {
            return Result.error("文件上传失败");
        }
        String suffix = originalFilename.substring(originalFilename.indexOf("."));//原文件后缀
        String newFileName = System.currentTimeMillis() + suffix;//文件新名称
        String filePath = BASE_FILE_PATH + newFileName;//文件所在路径
        try {
            FileUtil.mkParentDirs(filePath);  // 创建父级目录
            file.transferTo(FileUtil.file(filePath)); //生成文件
            return Result.success(newFileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.error("文件上传失败");
    }

    @GetMapping("/getUserById/{id}")
    public Result getUserById(@PathVariable Integer id){
        User user = userService.getUserById(id);
        return Result.success(user);
    }

    @PostMapping("/update")
    public Result update(@RequestBody User user){
        boolean result = userService.update(user);
        if (result){
            return Result.success();
        }else{
            return Result.error("保存失败");
        }
    }

    @GetMapping("/modifyPwdById/{id}/{pwd}")
    public Result modifyPwdById(@PathVariable Integer id,@PathVariable String pwd){
        boolean result = userService.modifyPwdById(id,pwd);
        if (result){
            return Result.success();
        }else{
            return Result.error("修改失败");
        }
    }

    @GetMapping("/ContributeList")
    public Result ContributeList(UserRequest userRequest){
        return Result.success(essayService.getEssaysByUid(userRequest));
    }

    @GetMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable Integer id){
        boolean result = essayService.deleteById(id);
        if (result){
            return Result.success();
        }else{
            return Result.error("删除失败");
        }
    }
}
