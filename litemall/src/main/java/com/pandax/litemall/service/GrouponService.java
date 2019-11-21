package com.pandax.litemall.service;

import com.pandax.litemall.bean.Groupon;
import com.pandax.reponseJson.GrouponGoods;

import java.util.List;

public interface GrouponService {
    List<Groupon> selectGrouponMy(Integer showType);

    List<GrouponGoods> selectAllGrouponList(Integer page, Integer size);
}
