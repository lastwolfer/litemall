package com.pandax.litemall.service;

import com.pandax.litemall.bean.Groupon;

import java.util.List;

public interface GrouponService {
    List<Groupon> selectGrouponMy(Integer showType);
}
