package com.pandax.litemall.service;

import com.pandax.litemall.bean.Role;
import com.pandax.litemall.bean.RoleInfo;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Da
 * @version 1.0
 * @date 2019/11/15
 * @time 21:20
 */

public interface RoleService {
    List<RoleInfo> selectRoles();

    HashMap<String, Object> getRoles(Integer page, Integer limit, String sort, String order, String name);

    Role updateRole(Role role);

    Map createRole(Role role);

    int deleteRole(Role role);

    Role selectRoleById(int id);

    Role selectRoleByName(String name);
}
