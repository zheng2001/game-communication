package com.example.service.impl;

import com.example.controller.back.request.BaseRequest;
import com.example.mapper.CommentMapper;
import com.example.mapper.DataMapper;
import com.example.mapper.EssayMapper;
import com.example.mapper.TagMapper;
import com.example.pojo.Data;
import com.example.pojo.Tag;
import com.example.service.TagService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    TagMapper tagMapper;
    @Autowired
    DataMapper dataMapper;
    @Autowired
    EssayMapper essayMapper;
    @Autowired
    CommentMapper commentMapper;

    @Override
    public PageInfo<Tag> list(BaseRequest baseRequest) {
        PageHelper.startPage(baseRequest.getPageNum(), baseRequest.getPageSize());
        List<Tag> tags = tagMapper.listByCondition(baseRequest);
        PageInfo<Tag> list = new PageInfo<>(tags);
        return list;
    }

    @Override
    public boolean addTag(Tag tag) {
        Data data = dataMapper.getValueIdByKeyAndValue("max_tag_order","最大标签序号");
        if (data == null){
            return false;
        }
        data.setValueId(data.getValueId()+1);
        tag.setOrder(data.getValueId());
        dataMapper.update(data);
        return tagMapper.addTag(tag);
    }

    @Override
    public boolean checkTagName(String tagName) {
        int count = tagMapper.checkTagName(tagName);
        return count>0;
    }

    @Override
    public boolean checkTagNameWithOutId(String tagName,int id) {
        int count = tagMapper.checkTagNameWithOutId(tagName,id);
        return count>0;
    }

    @Override
    public boolean batchDelByIds(Integer[] ids) {
        // 批量删除
        boolean flag1 = tagMapper.batchDelByIds(ids);
        // 删除标签对应的所有文章和文章评论
        List<Integer> essayIds = essayMapper.getIdsByTid(ids);
        if (essayIds.size()>0) {
            essayMapper.batchDelByIds(essayIds);
            commentMapper.batchDelByIds(essayIds);
        }

        // 删除后，标签重新排序
        boolean flag2;
        List<Integer> bannerIds = tagMapper.tagIds();
        if (bannerIds.size()>0){
            flag2 = tagMapper.formatOrder(bannerIds);
        }else{
            flag2 = true;
        }
        // 修改状态数据表最大标签序号
        Data data = dataMapper.getValueIdByKeyAndValue("max_tag_order","最大标签序号");
        if (data == null){
            return false;
        }
        // 获取最大标签排序序号
        Integer maxOrder = tagMapper.getTagMaxOrder();
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
    public boolean toUpTag(Integer id) {
        //根据id获取当前的标签
        Tag t = tagMapper.getTagById(id);
        if (t == null) {
            return false;
        }
        //获取当前标签的上方标签
        Tag upTag = tagMapper.getTagByGtOrder(t.getOrder());
        if (upTag == null) {
            return false;
        }
        //对换排序
        int tOrder = t.getOrder();
        t.setOrder(upTag.getOrder());
        upTag.setOrder(tOrder);
        //修改排序
        boolean flag1 = tagMapper.update(t);
        boolean flag2 = tagMapper.update(upTag);
        if (flag1 && flag2){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean toDownTag(Integer id) {
        //根据id获取当前的标签
        Tag t = tagMapper.getTagById(id);
        if (t == null) {
            return false;
        }
        //获取当前标签的下方标签
        Tag downTag = tagMapper.getTagByLtOrder(t.getOrder());
        if (downTag == null) {
            return false;
        }
        //对换排序
        int tOrder = t.getOrder();
        t.setOrder(downTag.getOrder());
        downTag.setOrder(tOrder);
        //修改排序
        boolean flag1 = tagMapper.update(t);
        boolean flag2 = tagMapper.update(downTag);
        if (flag1 && flag2){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Tag getTagById(Integer id) {
        return tagMapper.getTagById(id);
    }

    @Override
    public boolean update(Tag tag) {
        Tag t = tagMapper.getTagById(tag.getId());
        t.setTagName(tag.getTagName());
        return tagMapper.update(t);
    }

    @Override
    public boolean deleteById(Integer id) {
        boolean flag1 = tagMapper.deleteById(id);
        // 删除标签对应的所有文章和文章评论
        Integer[] ids = {id};
        List<Integer> essayIds = essayMapper.getIdsByTid(ids);
        if (essayIds.size()>0) {
            essayMapper.batchDelByIds(essayIds);
            commentMapper.batchDelByIds(essayIds);
        }

        // 删除后，标签重新排序
        boolean flag2;
        List<Integer> bannerIds = tagMapper.tagIds();
        if (bannerIds.size()>0){
            flag2 = tagMapper.formatOrder(bannerIds);
        }else{
            flag2 = true;
        }

        Data data = dataMapper.getValueIdByKeyAndValue("max_tag_order","最大标签序号");
        if (data == null){
            return false;
        }
        Integer maxOrder= tagMapper.getTagMaxOrder();
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
    public List<Tag> getTagsByOrderLtSix() {
        List<Tag> tags = tagMapper.getTagsByOrderLtSix();
        return tags;
    }

    @Override
    public List<Tag> getTagsByOrderGtFive() {
        List<Tag> tags = tagMapper.getTagsByOrderGtFive();
        return tags;
    }

    @Override
    public List<Tag> list() {
        List<Tag> tags = tagMapper.list();
        return tags;
    }
}
