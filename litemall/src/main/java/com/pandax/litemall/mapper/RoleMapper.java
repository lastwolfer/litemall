package com.pandax.litemall.mapper;

import com.pandax.litemall.bean.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Da
 * @version 1.0
 * @date 2019/11/15
 * @time 21:16
 */

public interface RoleMapper {
    List<Role> selectRoles();

    List<Role> selectRoleList(String sort, String order, @Param("name") String name);

    int updateRole(@Param("role") Role role);

    int createRole(@Param("role") Role role);

    Integer getLastInsertId();

    Role getRole(@Param("id") Integer id);

    int deleteRole(@Param("role") Role role);

    Role selectRoleById(@Param("id") int id);

    Role selectRoleByName(@Param("name") String name);
}
