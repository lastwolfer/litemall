package com.pandax.litemall.service;

import com.pandax.litemall.bean.Admin;
import com.pandax.litemall.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @author Da
 * @version 1.0
 * @date 2019/11/15
 * @time 17:28
 */

@Service
public class AdminServiceImpl implements AdminService{

    @Autowired
    AdminMapper adminMapper;


    @Override
    public HashMap<String, Object> queryUsers() {
        HashMap<String, Object> map = new HashMap<>();
        int total = (int) adminMapper.countByExample(null);
        map.put("total", total);
        List<Admin> adminList = adminMapper.selectByExample(null);
        map.put("items", adminList);
        return map;
    }
}
