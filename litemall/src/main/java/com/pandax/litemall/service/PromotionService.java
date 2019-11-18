package com.pandax.litemall.service;

import com.pandax.litemall.bean.*;

import java.util.Map;

public interface PromotionService {
    Map<String,Object> listAd(Integer page, Integer limit,String sort,String order, Ad ad);

    int deleteByPrimaryKey(Integer id);

    Map<String, Object> listCoupon(Integer page, Integer limit, String sort,String order,Coupon coupon);

    Coupon createCoupon(Coupon coupon);

    Coupon readCoupon(Integer id);

    Map<String, Object> listCouponUser(Integer page, Integer limit, String sort,String order,CouponUser couponUser);

    int deleteCoupon(Integer id);

    Coupon updateCoupon(Coupon coupon);

    Ad createAd(Ad record);

    Ad updateAd(Ad record);

    Map<String, Object> listTopic(Integer page, Integer limit, String sort,String order,Topic topic);

    Topic createTopic(Topic topic);

    Topic updateTopic(Topic topic);

    int deleteTopic(Integer id);

    Map<String, Object> listGroupon(Integer page, Integer limit, String sort,String order,GrouponRules grouponRules);

    GrouponRules createGrouponRules(GrouponRules grouponRules);

    GrouponRules editGroupon(GrouponRules grouponRules);

    int deleteGroupon(Integer id);

    Map<String, Object> listRecord(Integer page, Integer limit, String sort,String order,Groupon groupon);
}
