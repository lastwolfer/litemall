package com.pandax.litemall.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pandax.litemall.bean.*;
import com.pandax.litemall.mapper.*;
import org.apache.shiro.SecurityUtils;
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
    @Autowired
    OrderGoodsMapper orderGoodsMapper;

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
        List<Map<String,Object>> list = new ArrayList<>();
        HashMap<String, Object> map = new HashMap<>();
        HashMap<String, Object> map1 = new HashMap<>();
        if(groupon.getGoodsId()!=null) {
            Integer goodsId = groupon.getGoodsId();
            Goods goods = goodsMapper.selectByPrimaryKey(goodsId);
            GrouponRules grouponRules = grouponRulesMapper.selectByGoodsId(goodsId);
            if(goods == null){
                return null;
            }
            map1.put("goods",goods);
            map1.put("rules",grouponRules);
            example.setOrderByClause(sort+" "+order);
        }
        List<Groupon> list1 = grouponMapper.selectByExample(example);
        for (Groupon groupon1 : list1) {
            //通过团购活动取得订单id
            Integer orderId = groupon1.getOrderId();
            //通过订单id获得goodsId
            OrderGoods orderGoods = orderGoodsMapper.selectByOrderId(orderId);
            Integer goodsId = orderGoods.getGoodsId();
            //
            GrouponRules grouponRules = grouponRulesMapper.selectByGoodsId(goodsId);
            map1.put("rules",grouponRules);
            map1.put("groupon",groupon1);
        }
        PageInfo<Groupon> PageInfo = new PageInfo<>(list1);
        long total = PageInfo.getTotal();
        list.add(map1);
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
        PageInfo<CouponUser> pageInfo = new PageInfo<>(couponUserList);
        long total = pageInfo.getTotal();
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
        PageHelper.startPage(1,4);
        couponExample.createCriteria().andTotalGreaterThan(0).andStatusEqualTo(status);
        return couponMapper.selectByExample(couponExample);
    }

    /**
     * 团购信息
     * @return
     */
    @Override
    public List<GrouponList> selectGrouponList() {
        PageHelper.startPage(1,4);
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


    @Override
    public Map<String, Object> wxListCoupon(Integer page, Integer size, Coupon coupon) {
        PageHelper.startPage(page,size);
        //通过userId来取得用户的优惠券id
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        List<CouponUser> list = couponUserMapper.selectByUserId(user.getId());
        List<Coupon> couponList = new ArrayList<>();
        for (CouponUser couponUser : list) {
            Integer couponId = couponUser.getCouponId();
            CouponExample example = new CouponExample();
            example.createCriteria().andStatusEqualTo(coupon.getStatus()).andIdEqualTo(couponId);
            List<Coupon> couponList1 = couponMapper.selectByExample(example);
            for (Coupon coupon1 : couponList1) {
                if(coupon1.getStartTime()==null&&coupon1.getEndTime()==null){
                    coupon1.setStartTime(coupon1.getAddTime());
                    Calendar c = Calendar.getInstance();
                    c.setTime(coupon1.getAddTime());
                    c.add(Calendar.DAY_OF_MONTH,coupon1.getDays());//利用Calendar 实现 Date日期+1天  
                    coupon1.setEndTime(c.getTime());
                }
                couponList.add(coupon1);
            }
        }
        PageInfo<Coupon> PageInfo = new PageInfo<>(couponList);
        long total = PageInfo.getTotal();
        HashMap<String, Object> map = new HashMap<>();
        map.put("count",total);
        map.put("data",couponList);
        return map;
    }

    @Override
    public int wxReceiveCoupon(Integer couponId) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Coupon coupon = couponMapper.selectByPrimaryKey(couponId);
        if(coupon.getStartTime()==null&&coupon.getEndTime()==null){
            coupon.setStartTime(coupon.getAddTime());
            Calendar c = Calendar.getInstance();
            c.setTime(coupon.getAddTime());
            c.add(Calendar.DAY_OF_MONTH,coupon.getDays());//利用Calendar 实现 Date日期+1天  
            coupon.setEndTime(c.getTime());
        }
        CouponUser couponUser = new CouponUser();

        couponUser.setUserId(user.getId());
        couponUser.setCouponId(couponId);
        couponUser.setStatus(coupon.getStatus());
        couponUser.setStartTime(coupon.getStartTime());
        couponUser.setEndTime(coupon.getEndTime());
        couponUser.setAddTime(new Date());
        couponUser.setUpdateTime(new Date());
        couponUser.setDeleted(coupon.getDeleted());
        int i = couponUserMapper.insertSelective(couponUser);
        //如果插入成功，就相应减去优惠券数量1
        if(i == 1){
            coupon.setTotal(coupon.getTotal()-1);
            couponMapper.updateByPrimaryKeySelective(coupon);
        }
        return i;
    }

    @Override
    public int wxexchangeCoupon(String code) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        CouponExample couponExample = new CouponExample();
        couponExample.createCriteria().andCodeEqualTo(code);
        List<Coupon> list = couponMapper.selectByExample(couponExample);
        if(list != null) {
            for (Coupon coupon : list) {
                if (coupon.getStartTime() == null && coupon.getEndTime() == null) {
                    coupon.setStartTime(coupon.getAddTime());
                    Calendar c = Calendar.getInstance();
                    c.setTime(coupon.getAddTime());
                    c.add(Calendar.DAY_OF_MONTH, coupon.getDays());//利用Calendar 实现 Date日期+1天  
                    coupon.setEndTime(c.getTime());
                }
                CouponUser couponUser = new CouponUser();
                couponUser.setUserId(user.getId());
                couponUser.setCouponId(coupon.getId());
                couponUser.setStatus(coupon.getStatus());
                couponUser.setStartTime(coupon.getStartTime());
                couponUser.setEndTime(coupon.getEndTime());
                couponUser.setAddTime(new Date());
                couponUser.setUpdateTime(new Date());
                couponUser.setDeleted(coupon.getDeleted());
                int i = couponUserMapper.insertSelective(couponUser);
                return  i;
            }
        }
        return 0;
    }

    @Override
    public List<Coupon> wxSelectListCoupon1(Integer cartId, Integer grouponRulesId) {
        //通过userId来取得用户的优惠券id
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        List<CouponUser> list = couponUserMapper.selectByUserId(user.getId());
        List<Coupon> couponList = new ArrayList<>();
        for (CouponUser couponUser : list) {
            Integer couponId = couponUser.getCouponId();
            CouponExample example = new CouponExample();
            Short status = 0;
            example.createCriteria().andStatusEqualTo(status).andIdEqualTo(couponId);
            List<Coupon> couponList1 = couponMapper.selectByExample(example);
            for (Coupon coupon1 : couponList1) {
                if(coupon1.getStartTime()==null&&coupon1.getEndTime()==null){
                    coupon1.setStartTime(coupon1.getAddTime());
                    Calendar c = Calendar.getInstance();
                    c.setTime(coupon1.getAddTime());
                    c.add(Calendar.DAY_OF_MONTH,coupon1.getDays());//利用Calendar 实现 Date日期+1天  
                    coupon1.setEndTime(c.getTime());
                }
                couponList.add(coupon1);
            }
        }
        return couponList;
    }
}
