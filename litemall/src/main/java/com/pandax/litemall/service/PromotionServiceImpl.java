package com.pandax.litemall.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pandax.litemall.bean.*;
import com.pandax.litemall.mapper.AdMapper;
import com.pandax.litemall.mapper.CouponMapper;
import com.pandax.litemall.mapper.CouponUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PromotionServiceImpl implements PromotionService {

    @Autowired
    AdMapper adMapper;
    @Autowired
    CouponMapper couponMapper;
    @Autowired
    CouponUserMapper couponUserMapper;

    @Override
    public Map<String,Object> selectByExample(Integer page, Integer limit, Ad ad) {
        AdExample example = new AdExample();
        AdExample.Criteria criteria = example.createCriteria();
        if(ad.getName()!=null) {
            criteria.andNameLike("%" + ad.getName() + "%");
        }
        if(ad.getContent()!=null){
            criteria.andContentLike("%" + ad.getContent() + "%");
        }
        List<Ad> ads = adMapper.selectByExample(example);
        //完成分页的查询
        PageHelper.startPage(page,limit);
        PageInfo<Ad> adPageInfo = new PageInfo<>(ads);
        long total = adPageInfo.getTotal();
        HashMap<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("items",ads);
        return map;
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
         int delete = adMapper.deleteByPrimaryKey(id);
         return delete;
    }

    @Override
    public Map<String, Object> listCoupon(Integer page, Integer limit, Coupon coupon) {
        CouponExample example = new CouponExample();
        CouponExample.Criteria criteria = example.createCriteria();
        if(coupon.getName()!=null) {
            criteria.andNameLike("%" + coupon.getName() + "%");
        }
        if(coupon.getStatus()!=null){
            criteria.andStatusEqualTo(coupon.getStatus());
        }
        if(coupon.getType()!=null){
            criteria.andTypeEqualTo(coupon.getType());
        }
        List<Coupon> couponList = couponMapper.selectByExample(example);
        //完成分页的查询
        PageHelper.startPage(page,limit);
        PageInfo<Coupon> PageInfo = new PageInfo<>(couponList);
        long total = PageInfo.getTotal();
        HashMap<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("items",couponList);
        return map;
    }

    @Override
    public Coupon createCoupon(Coupon coupon) {
        coupon.setAddTime(new Date());
        coupon.setUpdateTime(new Date());
        couponMapper.insertSelective(coupon);
        int i = couponMapper.selectLastInsert();
        System.out.println(i);
        Coupon coupon1 = couponMapper.selectByPrimaryKey(i);
        return coupon1;
    }

    @Override
    public Coupon readCoupon(Integer id) {
        Coupon coupon = couponMapper.selectByPrimaryKey(id);
        return coupon;
    }

    @Override
    public Map<String, Object> listCouponUser(Integer page, Integer limit, CouponUser couponUser) {
        CouponUserExample example = new CouponUserExample();
        CouponUserExample.Criteria criteria = example.createCriteria();
        if(couponUser.getUserId()!=null) {
            criteria.andUserIdEqualTo(couponUser.getUserId());
        }
        if(couponUser.getStatus()!=null){
            criteria.andStatusEqualTo(couponUser.getStatus());
        }
        criteria.andCouponIdEqualTo(couponUser.getCouponId());
        List<CouponUser> couponUserList = couponUserMapper.selectByExample(example);
        //完成分页的查询
        PageHelper.startPage(page,limit);
        PageInfo<CouponUser> PageInfo = new PageInfo<>(couponUserList);
        long total = PageInfo.getTotal();
        HashMap<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("items",couponUserList);
        return map;
    }

    @Override
    public int deleteCoupon(Integer id) {
        int i = couponMapper.deleteByPrimaryKey(id);
        return i;
    }

    @Override
    public Coupon updateCoupon(Coupon coupon) {
        coupon.setUpdateTime(new Date());
        CouponExample example = new CouponExample();
        CouponExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(coupon.getId());
        int i = couponMapper.updateByExampleSelective(coupon, example);
        Coupon coupon1 = couponMapper.selectByPrimaryKey(coupon.getId());
        return coupon1;
    }
}
