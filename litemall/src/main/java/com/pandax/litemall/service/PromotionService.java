package com.pandax.litemall.service;

import com.pandax.litemall.bean.Ad;
import com.pandax.litemall.bean.Coupon;
import com.pandax.litemall.bean.CouponUser;

import java.util.Map;

public interface PromotionService {
    Map<String,Object> selectByExample(Integer page, Integer limit, Ad ad);

    int deleteByPrimaryKey(Integer id);

    Map<String, Object> listCoupon(Integer page, Integer limit, Coupon coupon);

    Coupon createCoupon(Coupon coupon);

    Coupon readCoupon(Integer id);

    Map<String, Object> listCouponUser(Integer page, Integer limit, CouponUser couponUser);

    int deleteCoupon(Integer id);

    Coupon updateCoupon(Coupon coupon);
}
