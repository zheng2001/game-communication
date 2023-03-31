package com.example.mapper;

import com.example.controller.back.request.BaseRequest;
import com.example.controller.front.request.CommunicateRequest;
import com.example.controller.front.request.UserRequest;
import com.example.mapper.po.ContributePassCountPO;
import com.example.pojo.Essay;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface EssayMapper {
    // 获取所有文章数量
    Integer getAllEssayCount();
    // 获取所有已通过文章数量
    Integer getPassedEssayCount();
    // 获取最近一周投稿文章信息
    List<ContributePassCountPO> getContribute();
    // 获取最近一周投稿通过文章信息
    List<ContributePassCountPO> getPass();
    // 获取tid下的所有文章id
    List<Integer> getIdsByTid(Integer[] ids);
    // 批量删除文章
    boolean batchDelByIds(List<Integer> ids);
    // 分页查询
    List<Essay> listByPage(BaseRequest baseRequest);
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
    List<Essay> getTopThreeByTidOutId(Integer id,Integer tid);
    // 查询uid用户下的所有投稿文章
    List<Essay> getEssaysByUid(UserRequest userRequest);
    // 根据id删除
    boolean deleteById(Integer id);
}
