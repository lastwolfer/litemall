package com.pandax.litemall.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pandax.litemall.bean.Log;
import com.pandax.litemall.bean.LogExample;
import com.pandax.litemall.mapper.LogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Da
 * @version 1.0
 * @date 2019/11/16
 * @time 16:56
 */

@Service
public class LogServiceImpl implements LogService{

    @Autowired
    LogMapper logMapper;

    @Override
    public void record(Log log) {
        logMapper.insert(log);
    }

    @Override
    public Map<String, Object> showLogList(Integer page, Integer limit,
                                           String sort, String order, String username) {
        PageHelper.startPage(page, limit);
        LogExample example = new LogExample();
        LogExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false);
        example.setOrderByClause(sort + " " + order);
        if (username != null) {
            criteria.andAdminLike("%" + username + "%");
        }
        List<Log> logList = logMapper.selectByExample(example);
        PageInfo<Log> logPageInfo = new PageInfo<>(logList);
        Map<String, Object> map = new HashMap<>();
        map.put("total", logPageInfo.getTotal());
        map.put("items", logList);
        return map;
    }


}
