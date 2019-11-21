package com.pandax.litemall.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pandax.litemall.bean.*;
import com.pandax.litemall.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PromotionServiceImpl implements PromotionService {

    @Autowired
    AdMapper adMapper;
    @Autowired
    CouponMapper couponMapper;
    @Autowired
    CouponUserMapper couponUserMapper;
    @Autowired
    TopicMapper topicMapper;
    @Autowired
    GrouponRulesMapper grouponRulesMapper;
    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    GrouponMapper grouponMapper;

    @Override
    public Map<String,Object> listAd(Integer page, Integer limit, String sort,String order,Ad ad) {
        PageHelper.startPage(page,limit);//分页
        AdExample example = new AdExample();
        AdExample.Criteria criteria = example.createCriteria();
        if(ad.getName()!=null) {
            criteria.andNameLike("%" + ad.getName() + "%");
        }
        if(ad.getContent()!=null){
            criteria.andContentLike("%" + ad.getContent() + "%");
        }
        example.setOrderByClause(sort+" "+order);//排序
        List<Ad> ads = adMapper.selectByExample(example);
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
    public Ad createAd(Ad record) {
        record.setAddTime(new Date());
        record.setUpdateTime(new Date());
        adMapper.insertSelective(record);
        int i = adMapper.selectLastInsert();
        Ad ad = adMapper.selectByPrimaryKey(i);
        return ad;
    }

    @Override
    public Ad updateAd(Ad record) {
        record.setUpdateTime(new Date());
        AdExample example = new AdExample();
        AdExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(record.getId());
        int i = adMapper.updateByExampleSelective(record, example);
        Ad ad = adMapper.selectByPrimaryKey(record.getId());
        return ad;
    }

    @Override
    public Map<String, Object> listTopic(Integer page, Integer limit, String sort,String order,Topic topic) {
        PageHelper.startPage(page,limit);
        TopicExample example = new TopicExample();
        TopicExample.Criteria criteria = example.createCriteria();
        if(topic.getTitle()!=null) {
            criteria.andTitleLike("%" + topic.getTitle() + "%");
        }
        if(topic.getSubtitle()!=null){
            criteria.andSubtitleLike("%" +topic.getSubtitle()+ "%");
        }
        example.setOrderByClause(sort+" "+order);
        List<Topic> list = topicMapper.selectByExample(example);
        PageInfo<Topic> PageInfo = new PageInfo<>(list);
        long total = PageInfo.getTotal();
        HashMap<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("items",list);
        return map;
    }

    @Override
    public Topic createTopic(Topic topic) {
        topic.setAddTime(new Date());
        topic.setUpdateTime(new Date());
        topicMapper.insertSelective(topic);
        int i = topicMapper.selectLastInsert();
        Topic topic1 = topicMapper.selectByPrimaryKey(i);
        return topic1;
    }

    @Override
    public Topic updateTopic(Topic record) {
        record.setUpdateTime(new Date());
        TopicExample example = new TopicExample();
        TopicExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(record.getId());
        int i = topicMapper.updateByExampleSelective(record, example);
        Topic topic = topicMapper.selectByPrimaryKey(record.getId());
        return topic;
    }

    @Override
    public int deleteTopic(Integer id) {
         return topicMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Map<String, Object> listGroupon(Integer page, Integer limit,String sort,String order, GrouponRules grouponRules) {
        PageHelper.startPage(page,limit);
        GrouponRulesExample example = new GrouponRulesExample();
        GrouponRulesExample.Criteria criteria = example.createCriteria();
        if(grouponRules.getGoodsId()!=null) {
                criteria.andGoodsIdEqualTo(grouponRules.getGoodsId());
            }
        example.setOrderByClause(sort+" "+order);
        List<GrouponRules> list = grouponRulesMapper.selectByExample(example);
        PageInfo<GrouponRules> PageInfo = new PageInfo<>(list);
        long total = PageInfo.getTotal();
        HashMap<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("items",list);
        return map;
    }

    @Override
    public GrouponRules createGrouponRules(GrouponRules grouponRules) {
        grouponRules.setAddTime(new Date());
        grouponRules.setUpdateTime(new Date());
        Goods goods;
        if(goodsMapper.selectByPrimaryKey(grouponRules.getGoodsId())==null){
            return null;
        }
        goods = goodsMapper.selectByPrimaryKey(grouponRules.getGoodsId());
        grouponRules.setPicUrl(goods.getPicUrl());
        grouponRules.setGoodsName(goods.getName());
        grouponRulesMapper.insertSelective(grouponRules);
        int i = grouponRulesMapper.selectLastInsert();
        GrouponRules result = grouponRulesMapper.selectByPrimaryKey(i);
        return result;
    }

    @Override
    public GrouponRules editGroupon(GrouponRules record) {
        record.setUpdateTime(new Date());
        GrouponRulesExample example = new GrouponRulesExample();
        GrouponRulesExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(record.getId());
        int i = grouponRulesMapper.updateByExampleSelective(record, example);
        GrouponRules result = grouponRulesMapper.selectByPrimaryKey(record.getId());
        return result;
    }

    @Override
    public int deleteGroupon(Integer id) {
        return grouponRulesMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Map<String, Object> listRecord(Integer page, Integer limit,String sort,String order, Groupon groupon) {
        PageHelper.startPage(page,limit);
        GrouponExample example = new GrouponExample();
        GrouponExample.Criteria criteria = example.createCriteria();
        if(groupon.getGoodsId()!=null) {
            //未完待续 等写前端的时候再说吧。。。。。。
        }
        example.setOrderByClause(sort+" "+order);
        List<Groupon> list = grouponMapper.selectByExample(example);
        PageInfo<Groupon> PageInfo = new PageInfo<>(list);
        long total = PageInfo.getTotal();
        HashMap<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("items",list);
        return map;
    }

    @Override
    public Map<String, Object> listCoupon(Integer page, Integer limit,String sort,String order, Coupon coupon) {
        PageHelper.startPage(page,limit);
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
        example.setOrderByClause(sort+" "+order);
        List<Coupon> couponList = couponMapper.selectByExample(example);
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
        Coupon coupon1 = couponMapper.selectByPrimaryKey(i);
        return coupon1;
    }

    @Override
    public Coupon readCoupon(Integer id) {
        Coupon coupon = couponMapper.selectByPrimaryKey(id);
        return coupon;
    }

    @Override
    public Map<String, Object> listCouponUser(Integer page, Integer limit, String sort,String order,CouponUser couponUser) {
        PageHelper.startPage(page,limit);
        CouponUserExample example = new CouponUserExample();
        CouponUserExample.Criteria criteria = example.createCriteria();
        if(couponUser.getUserId()!=null) {
            criteria.andUserIdEqualTo(couponUser.getUserId());
        }
        if(couponUser.getStatus()!=null){
            criteria.andStatusEqualTo(couponUser.getStatus());
        }
        criteria.andCouponIdEqualTo(couponUser.getCouponId());
        example.setOrderByClause(sort+" "+order);
        List<CouponUser> couponUserList = couponUserMapper.selectByExample(example);
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

    /**
     * 查询优惠券
     * @return
     */
    @Override
    public List<Coupon> selectCoupon() {
        CouponExample couponExample = new CouponExample();
        Short status = 0;
        couponExample.createCriteria().andTotalNotEqualTo(0).andStatusEqualTo(status);
        return couponMapper.selectByExample(couponExample);
    }

    /**
     * 团购信息
     * @return
     */
    @Override
    public List<GrouponList> selectGrouponList() {
        ArrayList<GrouponList> grouponLists = new ArrayList<>();
        List<GrouponRules> grouponRules = grouponRulesMapper.selectByExample(null);
        for (GrouponRules grouponRule : grouponRules) {
            GrouponList grouponList = new GrouponList();
            Integer goodsId = grouponRule.getGoodsId();
            Goods goods = goodsMapper.selectByPrimaryKey(goodsId);
            grouponList.setGroupon_price(goods.getRetailPrice().subtract(grouponRule.getDiscount()));
            grouponList.setGoods(goods);
            grouponList.setGrouponMember(grouponRule.getDiscountMember());
            grouponLists.add(grouponList);
        }
        return grouponLists;
    }
}
