package com.pandax.litemall.mapper;


import com.pandax.litemall.bean.Ad;

public interface AdMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Ad record);

    int insertSelective(Ad record);

    Ad selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Ad record);

    int updateByPrimaryKey(Ad record);
}