package com.pandax.litemall.service;

import com.pandax.litemall.mapper.GoodsProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Da
 * @version 1.0
 * @date 2019/11/15
 * @time 17:28
 */

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    GoodsProductMapper goodsProductMapper;

    @Override
    public int countProducts() {
        return (int) goodsProductMapper.countByExample(null);
    }
}
