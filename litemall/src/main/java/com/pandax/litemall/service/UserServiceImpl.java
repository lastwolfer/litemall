package com.pandax.litemall.service;

import com.pandax.litemall.mapper.CouponUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Da
 * @version 1.0
 * @date 2019/11/15
 * @time 17:28
 */

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    CouponUserMapper couponUserMapper;

    @Override
    public int countUsers() {
        return (int) couponUserMapper.countByExample(null);
    }
}
