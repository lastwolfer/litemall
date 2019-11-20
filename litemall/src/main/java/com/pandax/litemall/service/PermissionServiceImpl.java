package com.pandax.litemall.service;

import com.pandax.litemall.bean.Permission;
import com.pandax.litemall.bean.PermissionExample;
import com.pandax.litemall.bean.SystemPermissions;
import com.pandax.litemall.mapper.PermissionMapper;
import com.pandax.litemall.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Da
 * @version 1.0
 * @date 2019/11/19
 * @time 16:35
 */

@Service
public class PermissionServiceImpl implements PermissionService{

    @Autowired
    PermissionMapper permissionMapper;

    @Autowired
    RoleMapper roleMapper;

    @Override
    public void updatePermission(Integer roleId, List<String> list) {
        //permissionMapper.deletePermissionByRoleId(roleId);


        //现在这部分是直接让对应roleId的permission的deleted变成true
        //然后再插入，可以优化
        //但是考虑到，不会有那个有谁会对角色反复修改
        PermissionExample example = new PermissionExample();

        //这个查出所有与RoleId相关的权限
        example.createCriteria().andRoleIdEqualTo(roleId);
        List<Permission> permissions = permissionMapper.selectByExample(example);
        HashMap<String, Permission> map = new HashMap<>();
        for (Permission permission : permissions) {
            String key = permission.getPermission();
            map.put(key, permission);
        }
        ArrayList<Integer> deleteList = new ArrayList<>();
        ArrayList<Integer> resurgenceList = new ArrayList<>();
        ArrayList<String> insertList = new ArrayList<>();
        for (String s : list) {
            if(!map.containsKey(s)) {
                insertList.add(s);
            } else {
                Permission permission = map.get(s);
                if(permission.getDeleted()) {
                    resurgenceList.add(permission.getId());
                }
                map.put(s, null);
            }
        }
        Set<String> keys = map.keySet();
        for (String key : keys) {
            Permission permission = map.get(key);
            if(permission != null && !permission.getDeleted()) {
                deleteList.add(permission.getId());
            }
        }

        /*System.out.println(deleteList);
        System.out.println(resurgenceList);
        System.out.println(insertList);*/

        if (deleteList != null && deleteList.size() != 0) {
            permissionMapper.deletePermissionList(deleteList);
        }
        if (resurgenceList != null && resurgenceList.size() != 0) {
            permissionMapper.resurgencePermissionList(resurgenceList);
        }
        if (insertList != null && insertList.size() != 0) {
            permissionMapper.insertPermissionList(roleId, insertList);
        }
    }

    @Override
    public HashSet<String> selectPermissionsByRoleId(Integer[] roleIds) {
        PermissionExample example = new PermissionExample();
        List<Integer> list = new ArrayList<>();
        for (Integer roleId : roleIds) {
            list.add(roleId);
        }
        example.createCriteria().andDeletedEqualTo(false).andRoleIdIn(list);
        List<Permission> permissions = permissionMapper.selectByExample(example);
        HashSet<String> set = new HashSet<>();
        for (Permission permission : permissions) {
            set.add(permission.getPermission());
        }
        return set;
    }

    @Override
    public HashSet<String> selectSysPermissions() {
        List<SystemPermissions> permissionsList =
                roleMapper.selectPermissionsByNoParId();
        HashSet<String> urlSet = new HashSet<String>();
        for (SystemPermissions permissions : permissionsList) {
            permissions.setChildren(getChildrenPermissionsList(permissions.getId(),urlSet));
        }
        return urlSet;
    }


    private List<SystemPermissions> getChildrenPermissionsList(String id,HashSet<String> urlSet) {
        List<SystemPermissions> permissionsList =
                roleMapper.selectPermissionsByParId(id);
        for (SystemPermissions permissions : permissionsList) {
            if (permissions != null) {
                permissions.setChildren(getChildrenPermissionsList(permissions.getId(), urlSet));
                if(permissions.getApi() != null) {
                    urlSet.add(permissions.getApi());
                }
            }
        }
        return permissionsList;
    }
}
