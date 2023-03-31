package com.example.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.example.controller.back.request.BaseRequest;
import com.example.controller.front.request.CommunicateRequest;
import com.example.controller.front.request.UserRequest;
import com.example.mapper.EssayMapper;
import com.example.mapper.po.ContributePassCountPO;
import com.example.pojo.Essay;
import com.example.service.EssayService;
import com.example.util.CommonUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EssayServiceImpl implements EssayService {
    @Autowired
    EssayMapper essayMapper;

    @Override
    public Integer getAllEssayCount() {
        return essayMapper.getAllEssayCount();
    }

    @Override
    public Integer getPassedEssayCount() {
        return essayMapper.getPassedEssayCount();
    }

    @Override
    public Map<String, Object> getCount() {
        Map<String, Object> map = new HashMap<>();
        Date today = new Date();
        // rangeToList 返回从开始时间到结束时间的一个时间范围
        List<DateTime> dateRange = DateUtil.rangeToList(
                DateUtil.offsetDay(today, -6),  // offsetDay 计算时间的一个工具方法
                today, DateField.DAY_OF_WEEK);
        //  datetimeToDateStr() 就是一个处理的方法， 把 DateTime类型的List转换成一个 String的List
        List<String> dateStrRange = datetimeToDateStr(dateRange);
        map.put("date", dateStrRange);  // x轴的日期数据生产完毕

        List<ContributePassCountPO> contributeCount = essayMapper.getContribute();
        List<ContributePassCountPO> passCount = essayMapper.getPass();

        map.put("contribute", countList(contributeCount, dateStrRange));
        map.put("pass", countList(passCount, dateStrRange));
        return map;
    }
    // 把 DateTime类型的List转换成一个 String的List
    private List<String> datetimeToDateStr(List<DateTime> dateTimeList) {
        List<String> list = CollUtil.newArrayList();
        if (CollUtil.isEmpty(dateTimeList)) {
            return list;
        }
        for (DateTime dateTime : dateTimeList) {
            String date = DateUtil.formatDate(dateTime);
            list.add(date);
        }
        return list;
    }

    // 对数据库未统计的时间进行处理
    private List<Integer> countList(List<ContributePassCountPO> countPOList, List<String> dateRange) {
        List<Integer> list = CollUtil.newArrayList();
        if (CollUtil.isEmpty(countPOList)) {
            return list;
        }
        for (String date : dateRange) {
            // .map(ContributePassCountPO::getCount) 取出 对象里的 count值
            // orElse(0) 对没匹配的数据返回0
            // "2023-2-14" 没有的话 就返回0
            Integer count = countPOList.stream().filter(countPO -> date.equals(countPO.getDate()))
                    .map(ContributePassCountPO::getCount).findFirst().orElse(0);
            list.add(count);
        }
        // 最后返回的list的元素个数会跟 dateRange 的元素个数完全一样
        // dateRange: [
        //            "2023-2-20",
        //            "2023-2-21",
        //            "2023-2-22",
        //            "2023-2-23",
        //            "2023-2-24",
        //            "2023-2-25",
        //            "2023-2-26"
        //        ],
        // contribute: [
        //            0,
        //            0,
        //            2,
        //            1,
        //            0,
        //            1,
        //            3
        //        ]
        return list;
    }

    @Override
    public PageInfo<Essay> listByPage(BaseRequest baseRequest) {
        PageHelper.startPage(baseRequest.getPageNum(), baseRequest.getPageSize());
        List<Essay> essays = essayMapper.listByPage(baseRequest);
        PageInfo<Essay> list = new PageInfo<>(essays);
        return list;
    }

    @Override
    public Essay getEssayById(Integer id) {
        return essayMapper.getEssayById(id);
    }

    @Override
    public boolean update(Essay essay) {
        Essay e = essayMapper.getEssayById(essay.getId());
        e.setEssayStatus(essay.getEssayStatus());
        if (essay.getEssayStatus() == 1){
            e.setReleaseTime(CommonUtil.getNow());
        }
        e.setReason(essay.getReason());
        return essayMapper.update(e);
    }

    @Override
    public boolean contribute(Essay essay) {
        essay.setEssayStatus(0);
        essay.setCreateTime(CommonUtil.getNow());
        System.out.println(essay);
        return essayMapper.contribute(essay);
    }

    @Override
    public List<Essay> list(CommunicateRequest communicateRequest) {
        List<Essay> essays = essayMapper.list(communicateRequest);
        return essays;
    }

    @Override
    public Essay getEssayDetailsById(Integer id) {
        return essayMapper.getEssayDetailsById(id);
    }

    @Override
    public List<Essay> getTopThree(Integer id) {
        Essay e = essayMapper.getEssayById(id);
        List<Essay> essays = essayMapper.getTopThreeByTidOutId(id,e.getTid());
        return essays;
    }

    @Override
    public PageInfo<Essay> getEssaysByUid(UserRequest userRequest) {
        PageHelper.startPage(userRequest.getPageNum(), userRequest.getPageSize());
        List<Essay> essays = essayMapper.getEssaysByUid(userRequest);
        PageInfo<Essay> list = new PageInfo<>(essays);
        return list;
    }

    @Override
    public boolean deleteById(Integer id) {
        return essayMapper.deleteById(id);
    }

}
