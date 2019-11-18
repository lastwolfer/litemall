package com.pandax.litemall.service;

import com.pandax.litemall.bean.Admin;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;

/**
 * @author Da
 * @version 1.0
 * @date 2019/11/15
 * @time 17:27
 */

public interface AdminService {

    HashMap<String, Object> queryUsers(Integer page,
                                       Integer limit, String sort, String order, String username);

    void updateAdmin(Admin admin);

    void deleteAdmin(Admin admin);

    Admin createAdmin(Admin admin);

    int queryUserCountByName(String username);

    Admin queryUserCountById(Integer id);
}
