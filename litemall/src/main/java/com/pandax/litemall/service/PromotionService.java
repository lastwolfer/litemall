package com.pandax.litemall.service;

import com.pandax.litemall.bean.Ad;

import java.util.Map;

public interface PromotionService {
    int insertSelective(Ad record);
    Map<String,Object> selectByExample(Integer page, Integer limit, Ad ad);
    int deleteByPrimaryKey(Integer id);
}
