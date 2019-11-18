package com.pandax.litemall.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pandax.litemall.bean.Role;
import com.pandax.litemall.bean.RoleInfo;
import com.pandax.litemall.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public HashMap<String, Object> getRoles(Integer page, Integer limit, String sort, String order, String name) {
        PageHelper.startPage(page, limit);
        HashMap<String, Object> map = new HashMap<>();
        if(name!=null){
            name = "%" + name + "%";
        }
        List<Role> roleList = roleMapper.selectRoleList(sort,order,name);
        PageInfo<Role> rolePageInfo = new PageInfo<>(roleList);
        map.put("items", roleList);
        map.put("total", rolePageInfo.getTotal());
        return map;
    }

    @Override
    public Role updateRole(Role role) {
        int updateStatus = roleMapper.updateRole(role);
        if(updateStatus!=-1){
            return role;
        }
        return null;
    }

    @Override
    public Map createRole(Role role) {
        int insertStatus = roleMapper.createRole(role);
        Role createRole = null;
        if(insertStatus!=-1) {
            createRole = roleMapper.getRole(roleMapper.getLastInsertId());
        }
        Map<String,Object> map = new HashMap<>();
        map.put("id", createRole.getId());
        map.put("name", createRole.getName());
        map.put("desc", createRole.getDesc());
        map.put("addTime", createRole.getAddTime());
        map.put("updateTime", createRole.getUpdateTime());
        return map;
    }

    @Override
    public int deleteRole(Role role) {
        int deleteStatus = roleMapper.deleteRole(role);
        return deleteStatus;
    }
}
