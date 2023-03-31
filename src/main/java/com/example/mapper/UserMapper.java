package com.example.mapper;

import com.example.controller.back.request.BaseRequest;
import com.example.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    // 获取所有用户数量
    Integer getAllUserCount();
    // 模糊分页查询
    List<User> listByCondition(BaseRequest baseRequest);
    // 添加用户
    boolean addUser(User user);
    // 判断用户的用户名是否重复
    int checkUsername(String username);
    // 根据id查询用户
    User getUserById(int id);
    // 修改用户
    boolean update(User user);
    // 删除用户
    boolean deleteById(Integer id);
    /*前台*/
    // 登录
    User login(@Param("username")String username, @Param("pwd") String pwd);
    // 注册
    boolean register(User user);
    // 根据Id修改密码
    boolean modifyPwdById(int id, String pwd);
}
