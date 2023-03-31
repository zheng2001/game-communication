package com.example.service;


import com.example.controller.back.request.BaseRequest;
import com.example.controller.front.request.CommunicateRequest;
import com.example.controller.front.request.UserRequest;
import com.example.pojo.Essay;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface EssayService {
    // 获取所有文章数量
    Integer getAllEssayCount();
    // 获取所有已通过文章数量
    Integer getPassedEssayCount();
    // 获取最近一周投稿数量和通过数量
    Map<String, Object> getCount();
    // 分页查询
    PageInfo<Essay> listByPage(BaseRequest baseRequest);
    // 根据id查询文章
    Essay getEssayById(Integer id);
    // 修改文章
    boolean update(Essay Essay);

    /*前台*/
    // 用户投稿
    boolean contribute(Essay essay);
    // 模糊查询
    List<Essay> list(CommunicateRequest communicateRequest);
    // 根据id查询文章详情
    Essay getEssayDetailsById(Integer id);
    // 查询标签一样的前三个不包括自己
    List<Essay> getTopThree(Integer id);
    // 查询uid用户下的所有投稿文章
    PageInfo<Essay> getEssaysByUid(UserRequest userRequest);
    // 根据id删除
    boolean deleteById(Integer id);
}
