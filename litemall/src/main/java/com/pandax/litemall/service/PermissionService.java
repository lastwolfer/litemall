package com.pandax.litemall.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Da
 * @version 1.0
 * @date 2019/11/19
 * @time 16:35
 */

public interface PermissionService {
    void updatePermission(Integer roleId, List<String> list);

    HashSet<String> selectPermissionsByRoleId(Integer[] roleIds);

    HashSet<String> selectSysPermissions();

}
