package com.example.service;

import com.example.controller.back.request.BaseRequest;
import com.example.pojo.Banner;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface BannerService {
    // 分页查询
    PageInfo<Banner> list(BaseRequest baseRequest);
    // 添加轮播图
    boolean addBanner(Banner banner);
    // 批量删除轮播图
    boolean batchDelByIds(Integer[] ids);
    // 上移轮播图
    boolean toUpBanner(Integer id);
    // 下移轮播图
    boolean toDownBanner(Integer id);
    // 根据id查询轮播图
    Banner getBannerById(Integer id);
    // 修改轮播图
    boolean update(Banner banner);
    // 删除轮播图
    boolean deleteById(Integer id);
    /*前台*/
    // 查询所有轮播图
    List<Banner> banners();
}
