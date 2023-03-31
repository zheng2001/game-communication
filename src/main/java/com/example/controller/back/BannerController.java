package com.example.controller.back;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.example.common.Result;
import com.example.controller.back.request.BaseRequest;
import com.example.pojo.Banner;
import com.example.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.List;

@RestController
@RequestMapping(value = "/back/banner")
public class BannerController {
    @Autowired
    BannerService bannerService;

    private static final String BASE_FILE_PATH = System.getProperty("user.dir")+"/upload/banner/";

    @GetMapping("/list")
    public Result list(BaseRequest baseRequest){
        return Result.success(bannerService.list(baseRequest));
    }

    @PostMapping("/upload")
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

    @PostMapping("/add")
    public Result add(@RequestBody Banner banner){
        boolean result = bannerService.addBanner(banner);
        if (result){
            return Result.success();
        }else{
            return Result.error("添加失败");
        }
    }

    @GetMapping("/batchDel/{ids}")
    public Result batchDel(@PathVariable Integer[] ids){
        boolean result = bannerService.batchDelByIds(ids);
        if (result){
            return Result.success();
        }else{
            return Result.error("删除失败");
        }
    }

    @GetMapping("/getBannerById/{id}")
    public Result getBannerById(@PathVariable Integer id){
        Banner banner = bannerService.getBannerById(id);
        return Result.success(banner);
    }

    @GetMapping("/toUpBanner/{id}")
    public Result toUpBanner(@PathVariable Integer id){
        boolean result = bannerService.toUpBanner(id);
        if (result){
            return Result.success();
        }else{
            return Result.error("上移失败");
        }
    }

    @GetMapping("/toDownBanner/{id}")
    public Result toDownBanner(@PathVariable Integer id){
        boolean result =  bannerService.toDownBanner(id);
        if (result){
            return Result.success();
        }else{
            return Result.error("下移失败");
        }
    }

    @PostMapping("/update")
    public Result update(@RequestBody Banner banner){
        boolean result = bannerService.update(banner);
        if (result){
            return Result.success();
        }else{
            return Result.error("修改失败");
        }
    }

    @GetMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        boolean result = bannerService.deleteById(id);
        if (result){
            return Result.success();
        }else{
            return Result.error("删除失败");
        }
    }
}
