package com.pandax.litemall.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pandax.litemall.bean.*;
import com.pandax.litemall.mapper.PermissionMapper;
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

    @Autowired
    PermissionMapper permissionMapper;

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

    @Override
    public Role selectRoleById(int id) {
        return roleMapper.selectRoleById(id);
    }

    @Override
    public Role selectRoleByName(String name) {
        return roleMapper.selectRoleByName(name);
    }

    @Override
    public HashMap<String, Object> showPermissions(Integer roleId) {
        HashMap<String, Object> map = new HashMap<>();
        List<SystemPermissions> permissionsList =
                roleMapper.selectPermissionsByNoParId();
        for (SystemPermissions permissions : permissionsList) {
            permissions.setChildren(getChildrenPermissionsList(permissions.getId()));
        }
        map.put("systemPermissions", permissionsList);
        PermissionExample example = new PermissionExample();
        example.createCriteria().andRoleIdEqualTo(roleId).andDeletedEqualTo(false);
        List<Permission> permissions = permissionMapper.selectByExample(example);
        List<String> permissionName = new ArrayList<>();
        for (Permission permission : permissions) {
            permissionName.add(permission.getPermission());
        }
        map.put("assignedPermissions", permissionName);
        return map;
    }

    @Override
    public List<String> getRolesById(Integer[] roleIds) {
         return roleMapper.selectRolesById(roleIds);
    }


    private List<SystemPermissions> getChildrenPermissionsList(String id) {
        List<SystemPermissions> permissionsList =
                roleMapper.selectPermissionsByParId(id);
        for (SystemPermissions permissions : permissionsList) {
            if (permissions != null) {
                permissions.setChildren(getChildrenPermissionsList(permissions.getId()));
                if(permissions.getApi() != null) {
                    String[] split = permissions.getApi().split("/");
                    StringBuilder sb = new StringBuilder();
                    for (int i = 1; i < split.length; i++) {
                        sb.append(split[i]).append(":");
                    }
                    sb.delete(sb.length() - 1, sb.length());
                    permissions.setId(sb.toString());
                }
            }
        }
        return permissionsList;
    }
}
