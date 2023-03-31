package com.example.service;

import com.example.controller.back.request.BaseRequest;
import com.example.controller.front.dto.LoginDTO;
import com.example.controller.front.request.LoginRequest;
import com.example.pojo.User;
import com.github.pagehelper.PageInfo;

public interface UserService {
    // 获取所有用户数量
    Integer getAllUserCount();
    // 模糊分页查询
    PageInfo<User> list(BaseRequest baseRequest);
    // 添加用户
    boolean addUser(User user);
    // 判断用户的用户名是否重复
    boolean checkUsername(String username);
    // 根据id查询用户
    User getUserById(Integer id);
    // 修改用户
    boolean update(User user, Boolean isChange);
    // 删除用户
    boolean deleteById(Integer id);
    /*前台*/
    // 登录
    LoginDTO login(LoginRequest loginRequest);
    // 注册
    boolean register(User user);
    // 修改用户信息
    boolean update(User user);
    // 根据Id修改密码
    boolean modifyPwdById(Integer id, String pwd);
}
