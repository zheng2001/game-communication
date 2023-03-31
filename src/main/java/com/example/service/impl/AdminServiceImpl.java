package com.example.service.impl;

import com.example.util.CommonUtil;
import com.example.controller.back.dto.LoginDTO;
import com.example.controller.back.request.BaseRequest;
import com.example.controller.back.request.LoginRequest;
import com.example.exception.ServiceException;
import com.example.mapper.AdminMapper;
import com.example.pojo.Admin;
import com.example.service.AdminService;
import com.example.util.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminMapper adminMapper;

    @Override
    public PageInfo<Admin> list(BaseRequest baseRequest) {
        PageHelper.startPage(baseRequest.getPageNum(), baseRequest.getPageSize());
        List<Admin> admins = adminMapper.listByCondition(baseRequest);
        PageInfo<Admin> list = new PageInfo<>(admins);
        return list;
    }

    @Override
    public boolean addAdmin(Admin admin) {
        admin.setPwd(CommonUtil.setMd5Code(admin.getPwd())); // 设置md5加密
        admin.setCreateTime(CommonUtil.getNow());
        return adminMapper.addAdmin(admin);
    }

    @Override
    public boolean checkUsername(String username) {
        int count = adminMapper.checkUsername(username);
        return count>0;
    }

    @Override
    public Admin getAdminById(Integer id) {
        return adminMapper.getAdminById(id);
    }

    @Override
    public boolean update(Admin admin, Boolean isChange) {
        Admin ad = adminMapper.getAdminById(admin.getId());
        if (isChange){
            ad.setPwd(CommonUtil.setMd5Code(admin.getPwd())); // 设置md5加密
        }
        ad.setEmail(admin.getEmail());
        ad.setAdminStatus(admin.getAdminStatus());
        return adminMapper.update(ad);
    }

    @Override
    public boolean deleteById(Integer id) {
        return adminMapper.deleteById(id);
    }

    @Override
    public LoginDTO login(LoginRequest loginRequest) {
        loginRequest.setPwd(CommonUtil.setMd5Code(loginRequest.getPwd()));// 设置md5加密
        Admin admin = adminMapper.login(loginRequest.getUsername(),loginRequest.getPwd());
        if (admin == null){
            throw new ServiceException("用户名或密码错误!");
        }
        if(admin.getAdminStatus() == 0){
            throw new ServiceException("当前用户处于禁用状态,请联系管理员!");
        }
        LoginDTO loginDTO = new LoginDTO();
        BeanUtils.copyProperties(admin,loginDTO);
        // 生成token
        String token = TokenUtils.genToken(String.valueOf(admin.getId()), admin.getPwd());
        loginDTO.setToken(token);
        // 修改最近登录时间
        admin.setLoginTime(CommonUtil.getNow());
        boolean updateResult = adminMapper.update(admin);
        if (!updateResult){
            throw new ServiceException("登录时间修改异常!");
        }
        return loginDTO;
    }
}
