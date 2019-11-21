package com.pandax.litemall.service;

import com.github.pagehelper.PageHelper;
import com.pandax.litemall.bean.*;
import com.pandax.litemall.mapper.GoodsMapper;
import com.pandax.litemall.mapper.GrouponMapper;
import com.pandax.litemall.mapper.GrouponRulesMapper;
import com.pandax.reponseJson.GrouponGoods;
import com.pandax.reponseJson.ResponseGrouponGoods;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
public class GrouponServiceImpl implements GrouponService{
    @Autowired
    GrouponMapper grouponMapper;
    @Autowired
    GrouponRulesMapper grouponRulesMapper;
    @Autowired
    GoodsMapper goodsMapper;


    @Override
    public List<Groupon> selectGrouponMy(Integer showType) {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        List<Groupon> groupons = null;
        //判断是否是发起的团购
        if(showType == 0) {//发起的团购
            GrouponExample grouponExample = new GrouponExample();
            grouponExample.createCriteria().andCreatorUserIdEqualTo(user.getId());
            groupons = grouponMapper.selectByExample(grouponExample);
        }else if(showType == 1){//参与的团购
            GrouponExample grouponExample = new GrouponExample();
            grouponExample.createCriteria().andUserIdEqualTo(user.getId());
            groupons = grouponMapper.selectByExample(grouponExample);
        }
        for (Groupon groupon : groupons) {//只保留父团购
            if(groupon.getId() != groupon.getGrouponId()){
                groupons.remove(groupon);
            }
        }
        return groupons;
    }

    @Override
    public List<GrouponGoods> selectAllGrouponList(Integer page, Integer size) {
        PageHelper.startPage(page,size);
        List<GrouponRules> grouponRules = grouponRulesMapper.selectByExample(null);
        List<GrouponGoods> grouponGoods = new ArrayList<>();
        GrouponGoods groupon = null;
        for (GrouponRules grouponRule : grouponRules) {
            Integer goodsId = grouponRule.getGoodsId();
            Goods goods = goodsMapper.selectByPrimaryKey(goodsId);
            ResponseGrouponGoods responseGrouponGoods= new ResponseGrouponGoods();
            responseGrouponGoods.setId(goods.getId());
            responseGrouponGoods.setBrief(goods.getBrief());
            responseGrouponGoods.setName(goods.getName());
            responseGrouponGoods.setCounterPrice(goods.getCounterPrice());
            responseGrouponGoods.setRetailPrice(goods.getRetailPrice());
            responseGrouponGoods.setPicUrl(goods.getPicUrl());
            groupon = new GrouponGoods();
            groupon.setGoods(responseGrouponGoods);//goods  ok
            BigDecimal subtract = goods.getRetailPrice().subtract(grouponRule.getDiscount());
            groupon.setGroupon_price(subtract);//groupon_price   ok
            GrouponExample grouponExample = new GrouponExample();
            grouponExample.createCriteria().andRulesIdEqualTo(grouponRule.getId());
            long count = grouponMapper.countByExample(grouponExample);
            groupon.setGroupon_member(count);//groupon_price   ok
            grouponGoods.add(groupon);//
        }
        return grouponGoods;
    }
}
