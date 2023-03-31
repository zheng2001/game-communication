package com.example.controller.front;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.example.common.Result;
import com.example.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.List;

@RestController
@RequestMapping(value = "/front/home")
public class FrontHomeController {
    @Autowired
    BannerService bannerService;

    private static final String BASE_FILE_PATH = System.getProperty("user.dir")+"/upload/banner/";

    @GetMapping("/banners")
    public Result banners(){
        return Result.success(bannerService.banners());
    }

    @GetMapping("/download/{flag}")
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
}
