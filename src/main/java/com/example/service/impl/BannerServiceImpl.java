package com.example.service.impl;

import com.example.controller.back.request.BaseRequest;
import com.example.mapper.BannerMapper;
import com.example.mapper.DataMapper;
import com.example.pojo.Banner;
import com.example.pojo.Data;
import com.example.service.BannerService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BannerServiceImpl implements BannerService {
    @Autowired
    BannerMapper bannerMapper;
    @Autowired
    DataMapper dataMapper;

    @Override
    public PageInfo<Banner> list(BaseRequest baseRequest) {
        PageHelper.startPage(baseRequest.getPageNum(), baseRequest.getPageSize());
        List<Banner> banners = bannerMapper.list(baseRequest);
        PageInfo<Banner> list = new PageInfo<>(banners);
        return list;
    }

    @Override
    public boolean addBanner(Banner banner) {
        Data data = dataMapper.getValueIdByKeyAndValue("max_banner_order","最大轮播图序号");
        if (data == null){
            return false;
        }
        data.setValueId(data.getValueId()+1);
        banner.setOrder(data.getValueId());
        dataMapper.update(data);
        return bannerMapper.addBanner(banner);
    }

    @Override
    public boolean batchDelByIds(Integer[] ids) {
        // 批量删除
        boolean flag1 = bannerMapper.batchDelByIds(ids);
        // 删除后，轮播图重新排序
        boolean flag2;
        List<Integer> bannerIds = bannerMapper.bannerIds();
        if (bannerIds.size()>0){
            flag2 = bannerMapper.formatOrder(bannerIds);
        }else{
            flag2 = true;
        }

        // 修改状态数据表最大轮播图序号
        Data data = dataMapper.getValueIdByKeyAndValue("max_banner_order","最大轮播图序号");
        if (data == null){
            return false;
        }
        // 获取最大轮播图排序序号
        Integer maxOrder = bannerMapper.getBannerMaxOrder();
        if (maxOrder == null){
            maxOrder = 0;
        }
        data.setValueId(maxOrder);
        boolean flag3 = dataMapper.update(data);

        if (flag1 && flag2 && flag3){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean toUpBanner(Integer id) {
        //根据id获取当前的轮播图
        Banner b = bannerMapper.getBannerById(id);
        if (b == null) {
            return false;
        }
        //获取当前轮播图的上方轮播图
        Banner upBanner = bannerMapper.getBannerByGtOrder(b.getOrder());
        if (upBanner == null) {
            return false;
        }
        //对换排序
        int bOrder = b.getOrder();
        b.setOrder(upBanner.getOrder());
        upBanner.setOrder(bOrder);
        //修改排序
        boolean flag1 = bannerMapper.update(b);
        boolean flag2 = bannerMapper.update(upBanner);
        if (flag1 && flag2){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean toDownBanner(Integer id) {
        //根据id获取当前的轮播图
        Banner b = bannerMapper.getBannerById(id);
        if (b == null) {
            return false;
        }
        //获取当前轮播图的下方轮播图
        Banner downBanner = bannerMapper.getBannerByLtOrder(b.getOrder());
        if (downBanner == null) {
            return false;
        }
        //对换排序
        int bOrder = b.getOrder();
        b.setOrder(downBanner.getOrder());
        downBanner.setOrder(bOrder);
        //修改排序
        boolean flag1 = bannerMapper.update(b);
        boolean flag2 = bannerMapper.update(downBanner);
        if (flag1 && flag2){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Banner getBannerById(Integer id) {
        return bannerMapper.getBannerById(id);
    }

    @Override
    public boolean update(Banner banner) {
        Banner b = bannerMapper.getBannerById(banner.getId());
        b.setBannerImg(banner.getBannerImg());
        b.setBannerStatus(banner.getBannerStatus());
        return bannerMapper.update(b);
    }

    @Override
    public boolean deleteById(Integer id) {
        boolean flag1 = bannerMapper.deleteById(id);
        // 删除后，轮播图重新排序
        boolean flag2;
        List<Integer> bannerIds = bannerMapper.bannerIds();
        if (bannerIds.size()>0){
            flag2 = bannerMapper.formatOrder(bannerIds);
        }else{
            flag2 = true;
        }

        Data data = dataMapper.getValueIdByKeyAndValue("max_banner_order","最大轮播图序号");
        if (data == null){
            return false;
        }
        Integer maxOrder= bannerMapper.getBannerMaxOrder();
        if (maxOrder == null){
            maxOrder = 0;
        }
        data.setValueId(maxOrder);
        boolean flag3 = dataMapper.update(data);
        if (flag1 && flag2 && flag3){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public List<Banner> banners() {
        List<Banner> banners = bannerMapper.banners();
        return banners;
    }

}
