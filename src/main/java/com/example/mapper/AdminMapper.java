package com.example.mapper;

import com.example.controller.back.request.BaseRequest;
import com.example.pojo.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface AdminMapper {
    // 模糊分页查询
    List<Admin> listByCondition(BaseRequest baseRequest);
    // 添加管理员
    boolean addAdmin(Admin admin);
    // 判断管理员用户名是否重复
    int checkUsername(String username);
    // 根据id查询管理员
    Admin getAdminById(int id);
    // 修改管理员
    boolean update(Admin admin);
    // 根据id删除管理员
    boolean deleteById(Integer id);
    // 根据用户名和密码查询管理员
    Admin login(@Param("username")String username,@Param("pwd") String pwd);
}
