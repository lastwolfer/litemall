package com.pandax.litemall.mapper;

import com.pandax.litemall.bean.Admin;
import com.pandax.litemall.bean.AdminExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface AdminMapper {

    List<Admin> selectAdmins(@Param("sort") String sort, @Param("order") String order, @Param("username") String username);

    void updateAdmin(Admin admin);

    void deleteAdmin(Integer id);

    void createAdmin(@Param("admin") Admin admin);

    int selectLastInsertId();

    Admin selectAdminById(@Param("id") int id);

    Admin selectAdminByName(@Param("username") String username);
}
