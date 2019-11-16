package com.pandax.litemall.mapper;

import com.pandax.litemall.bean.Admin;
import com.pandax.litemall.bean.AdminExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface AdminMapper {

    List<Admin> selectAdmins(@Param("sort") String sort, @Param("order") String order);
}
