package com.example.mapper;

import com.example.controller.back.request.BaseRequest;
import com.example.pojo.Banner;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BannerMapper {
    // 分页查询
    List<Banner> list(BaseRequest baseRequest);
    // 添加轮播图
    boolean addBanner(Banner banner);
    // 批量删除轮播图
    boolean batchDelByIds(Integer[] ids);
    // 所有轮播图id
    List<Integer> bannerIds();
    // 重新排序轮播图
    boolean formatOrder(List<Integer> ids);
    // 获取轮播图最大排序号
    Integer getBannerMaxOrder();
    // 获取大于当前轮播图的第一个轮播图
    Banner getBannerByGtOrder(Integer order);
    // 获取小于当前轮播图的第一个轮播图
    Banner getBannerByLtOrder(Integer order);
    // 根据id查询轮播图
    Banner getBannerById(int id);
    // 修改轮播图
    boolean update(Banner banner);
    // 删除轮播图
    boolean deleteById(Integer id);
    /*前台*/
    // 查询所有轮播图
    List<Banner> banners();
}
