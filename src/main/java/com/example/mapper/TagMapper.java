package com.example.mapper;

import com.example.controller.back.request.BaseRequest;
import com.example.pojo.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TagMapper {
    // 模糊分页查询
    List<Tag> listByCondition(BaseRequest baseRequest);
    // 添加用户
    boolean addTag(Tag tag);
    // 判断标签名是否重复
    int checkTagName(String tagName);
    // 判断该id以外的标签名是否重复
    int checkTagNameWithOutId(String tagName,int id);
    // 批量删除标签
    boolean batchDelByIds(Integer[] ids);
    // 所有标签id
    List<Integer> tagIds();
    // 重新排序标签
    boolean formatOrder(List<Integer> ids);
    // 获取标签最大排序号
    Integer getTagMaxOrder();
    // 获取大于当前标签的第一个标签
    Tag getTagByGtOrder(Integer order);
    // 获取小于当前标签的第一个标签
    Tag getTagByLtOrder(Integer order);
    // 根据id查询标签
    Tag getTagById(int id);
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
