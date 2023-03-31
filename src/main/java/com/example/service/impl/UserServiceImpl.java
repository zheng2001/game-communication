package com.example.service.impl;

import com.example.controller.front.request.LoginRequest;
import com.example.controller.front.dto.LoginDTO;
import com.example.exception.ServiceException;
import com.example.util.CommonUtil;
import com.example.controller.back.request.BaseRequest;
import com.example.mapper.UserMapper;
import com.example.pojo.User;
import com.example.service.UserService;
import com.example.util.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public Integer getAllUserCount() {
        return userMapper.getAllUserCount();
    }

    @Override
    public PageInfo<User> list(BaseRequest baseRequest) {
        PageHelper.startPage(baseRequest.getPageNum(), baseRequest.getPageSize());
        List<User> users = userMapper.listByCondition(baseRequest);
        PageInfo<User> list = new PageInfo<>(users);
        return list;
    }

    @Override
    public boolean addUser(User user) {
        user.setPwd(CommonUtil.setMd5Code(user.getPwd()));// 设置md5加密
        user.setCreateTime(CommonUtil.getNow());
        return userMapper.addUser(user);
    }

    @Override
    public boolean checkUsername(String username) {
        int count = userMapper.checkUsername(username);
        return count>0;
    }

    @Override
    public User getUserById(Integer id) {
        return userMapper.getUserById(id);
    }

    @Override
    public boolean update(User user, Boolean isChange) {
        User us = userMapper.getUserById(user.getId());
        us.setNickname(user.getNickname());
        if (isChange){
            us.setPwd(CommonUtil.setMd5Code(user.getPwd())); // 设置md5加密
        }
        us.setAge(user.getAge());
        us.setSex(user.getSex());
        us.setHeadImg(user.getHeadImg());
        us.setUserStatus(user.getUserStatus());
        return userMapper.update(us);
    }

    @Override
    public boolean deleteById(Integer id) {
        return userMapper.deleteById(id);
    }

    @Override
    public LoginDTO login(LoginRequest loginRequest) {
        loginRequest.setPwd(CommonUtil.setMd5Code(loginRequest.getPwd()));// 设置md5加密
        User user = userMapper.login(loginRequest.getUsername(),loginRequest.getPwd());
        if (user == null){
            throw new ServiceException("用户名或密码错误!");
        }
        if(user.getUserStatus() == 0){
            throw new ServiceException("当前用户处于禁用状态,请联系管理员!");
        }
        LoginDTO loginDTO = new LoginDTO();
        BeanUtils.copyProperties(user,loginDTO);
        // 生成token
        String token = TokenUtils.genToken(String.valueOf(user.getId()), user.getPwd());
        loginDTO.setToken(token);
        // 修改最近登录时间
        user.setLoginTime(CommonUtil.getNow());
        boolean updateResult = userMapper.update(user);
        if (!updateResult){
            throw new ServiceException("登录时间修改异常!");
        }
        return loginDTO;
    }

    @Override
    public boolean register(User user) {
        // 设置默认头像、性别、年龄、用户状态
        user.setHeadImg("default.jpg");
        user.setSex("男");
        user.setAge(1);
        user.setUserStatus(1);

        user.setPwd(CommonUtil.setMd5Code(user.getPwd()));// 设置md5加密
        user.setCreateTime(CommonUtil.getNow());
        return userMapper.register(user);
    }

    @Override
    public boolean update(User user) {
        User u = userMapper.getUserById(user.getId());
        u.setNickname(user.getNickname());
        u.setAge(user.getAge());
        u.setSex(user.getSex());
        u.setHeadImg(user.getHeadImg());
        return userMapper.update(u);
    }

    @Override
    public boolean modifyPwdById(Integer id, String pwd) {
        return userMapper.modifyPwdById(id,CommonUtil.setMd5Code(pwd));
    }
}
