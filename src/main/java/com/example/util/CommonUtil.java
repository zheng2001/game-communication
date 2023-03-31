package com.example.util;

import cn.hutool.crypto.SecureUtil;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtil {
    private static final String PASS_SALT = "0123456789qwertyuiopasdfghjklzxcvbnm"; // 加密盐

    // 获取当前时间
    public static String getNow() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }
    // 设置md5加密
    public static String setMd5Code(String pwd){
        return SecureUtil.md5(pwd + PASS_SALT);
    }
}
