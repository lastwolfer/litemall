package com.pandax.litemall.service;

import com.pandax.litemall.bean.Role;
import com.pandax.litemall.bean.RoleInfo;
import com.pandax.litemall.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Da
 * @version 1.0
 * @date 2019/11/15
 * @time 21:21
 */

@Service
public class RoleServiceImpl implements RoleService{

    @Autowired
    RoleMapper roleMapper;

    @Override
    public List<RoleInfo> selectRoles() {
        ArrayList<RoleInfo> roleInfoArrayList = new ArrayList<>();
        List<Role> roleList = roleMapper.selectRoles();
        for (Role role : roleList) {
            RoleInfo roleInfo = new RoleInfo();
            roleInfo.setValue(role.getId());
            roleInfo.setLabel(role.getName());
            roleInfoArrayList.add(roleInfo);
        }
        return roleInfoArrayList;
    }
}
