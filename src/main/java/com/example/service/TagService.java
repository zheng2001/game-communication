package com.example.service;

import com.example.controller.back.request.BaseRequest;
import com.example.pojo.Tag;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface TagService {
    // 模糊分页查询
    PageInfo<Tag> list(BaseRequest baseRequest);
    // 添加用户
    boolean addTag(Tag tag);
    // 判断标签名是否重复
    boolean checkTagName(String tagName);
    // 判断该id以外的标签名是否重复
    boolean checkTagNameWithOutId(String tagName,int id);
    // 批量删除标签
    boolean batchDelByIds(Integer[] ids);
    // 上移标签
    boolean toUpTag(Integer id);
    // 下移标签
    boolean toDownTag(Integer id);
    // 根据id查询标签
    Tag getTagById(Integer id);
    // 修改标签
    boolean update(Tag tag);
    // 删除标签
    boolean deleteById(Integer id);
    /*前台*/
    // 查询前五个标签
    List<Tag> getTagsByOrderLtSix();
    // 查询所有标签，除了前五个标签
    List<Tag> getTagsByOrderGtFive();
    // 查询所有标签
    List<Tag> list();
}
