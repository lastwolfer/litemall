package com.pandax.litemall.service;

import com.pandax.litemall.bean.AdminExample;
import com.pandax.litemall.bean.GoodsExample;
import com.pandax.litemall.mapper.AdminMapper;
import com.pandax.litemall.mapper.GoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Da
 * @version 1.0
 * @date 2019/11/15
 * @time 17:28
 */

@Service
public class GoodsServiceImpl implements GoodsService{

    @Autowired
    GoodsMapper goodsMapper;

    public int countGoods(){
        return (int) goodsMapper.countByExample(null);
    }

}
