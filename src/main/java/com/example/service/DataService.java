package com.example.service;

import com.example.pojo.Data;

import java.util.List;

public interface DataService {
    // 根据状态键查找状态
    List<Data> getDataByKey(String key);
}
