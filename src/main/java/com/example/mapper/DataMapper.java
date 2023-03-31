package com.example.mapper;

import com.example.pojo.Data;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface DataMapper {

    // 根据状态键查找状态
    List<Data> getDataByKey(String key);
    // 根据状态键和状态值查找状态id
    Data getValueIdByKeyAndValue(String key,String value);
    // 根据id修改
    boolean update(Data data);
}
