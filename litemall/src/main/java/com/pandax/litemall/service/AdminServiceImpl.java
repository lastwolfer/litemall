package com.pandax.litemall.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pandax.litemall.bean.Admin;
import com.pandax.litemall.bean.AdminExample;
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
    public HashMap<String, Object> queryUsers(Integer page, Integer limit,
                                              String sort, String order, String username) {
        PageHelper.startPage(page, limit);
        HashMap<String, Object> map = new HashMap<>();
        if (username != null) {
            username = "%" + username + "%";
        }
        List<Admin> adminList = adminMapper.selectAdmins(sort, order, username);
        /*for (Admin admin : adminList) {
            System.out.println(admin);
        }*/
        PageInfo<Admin> adminPageInfo = new PageInfo<>(adminList);
        map.put("items", adminList);
        map.put("total", adminPageInfo.getTotal());
        return map;
    }

    @Override
    public void updateAdmin(Admin admin) {
        adminMapper.updateAdmin(admin);
    }

    @Override
    public void deleteAdmin(Admin admin) {
        adminMapper.deleteAdmin(admin.getId());
    }
}
