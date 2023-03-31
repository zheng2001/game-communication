package com.example.service;

import com.example.controller.back.dto.LoginDTO;
import com.example.controller.back.request.BaseRequest;
import com.example.controller.back.request.LoginRequest;
import com.example.pojo.Admin;
import com.github.pagehelper.PageInfo;

public interface AdminService {
    // 模糊分页查询
    PageInfo<Admin> list(BaseRequest baseRequest);
    // 添加管理员
    boolean addAdmin(Admin admin);
    // 判断管理员用户名是否重复
    boolean checkUsername(String username);
    // 根据id查询管理员
    Admin getAdminById(Integer id);
    // 修改管理员
    boolean update(Admin admin, Boolean isChange);
    // 删除管理员
    boolean deleteById(Integer id);
    // 登录
    LoginDTO login(LoginRequest loginRequest);
}
