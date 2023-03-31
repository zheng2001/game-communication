package com.example.service.impl;

import com.example.mapper.DataMapper;
import com.example.pojo.Data;
import com.example.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DataServiceImpl implements DataService {
    @Autowired
    DataMapper dataMapper;
    @Override
    public List<Data> getDataByKey(String key) {
        return dataMapper.getDataByKey(key);
    }
}
