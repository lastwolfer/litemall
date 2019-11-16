package com.pandax.litemall.service;

import com.pandax.litemall.bean.Role;
import com.pandax.litemall.bean.RoleInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Da
 * @version 1.0
 * @date 2019/11/15
 * @time 21:20
 */

public interface RoleService {
    List<RoleInfo> selectRoles();
}
