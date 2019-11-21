package com.pandax.litemall.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pandax.litemall.bean.*;
import com.pandax.litemall.mapper.CartMapper;
import com.pandax.litemall.mapper.CollectMapper;
import com.pandax.litemall.mapper.GoodsMapper;
import com.pandax.litemall.mapper.GoodsProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CartServiceImpl implements CartService{

    @Autowired
    CartMapper cartMapper;
    @Override
    public Map<String, Object> cartIndex() {
        CartTotal cartTotal = new CartTotal();

        //获取所有的Cart
        List<Cart> cartList=cartMapper.selectAllCart();
        cartTotal.setGoodsCount(cartList.size());
        cartTotal.setCheckedGoodsCount(cartList.size());
        Double sum=0.0;
        if(cartList!=null){
            for (Cart cart : cartList) {
                sum=sum+cart.getPrice().doubleValue()*cart.getNumber();
                System.out.println(cart.getPrice());
            }
        }
        cartTotal.setCheckedGoodsAmount(sum);
        cartTotal.setGoodsAmount(sum);
        HashMap<String, Object> map = new HashMap<>();

        map.put("cartTotal",cartTotal);
        map.put("cartList",cartList);
        return map;
    }

    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    GoodsProductMapper productMapper;
    @Override
    public int cartAdd(Cart cart) {
        //将无关的参数进行写入
        cart.setAddTime(new Date());
        cart.setUpdateTime(new Date());
        cart.setDeleted(false);
        //通过goodsId获取goods
        Goods goods = goodsMapper.selectByPrimaryKey(cart.getGoodsId());
        //通过productId获取product
        GoodsProduct goodsProduct = productMapper.selectByPrimaryKey(cart.getProductId());

        cart.setGoodsSn(goods.getGoodsSn());
        cart.setGoodsName(goods.getName());
        Double i = cart.getNumber() * goods.getRetailPrice().doubleValue();
        cart.setPrice(i);
        StringBuilder sb=new StringBuilder();
        for (String specification : goodsProduct.getSpecifications()) {
            sb.append(specification);
        }
        cart.setSpecifications(sb.toString());
        cart.setChecked(false);
        cart.setPicUrl(goods.getPicUrl());
        cartMapper.insert(cart);

        //获取所有订单
        List<Cart> cartList=cartMapper.selectAllCart();
        int number=0;
        for (Cart cart1 : cartList) {
            number+=cart1.getNumber();
        }
        return number;
    }

    @Override
    public int cartFastadd(Cart cart) {
        //将无关的参数进行写入
        cart.setAddTime(new Date());
        cart.setUpdateTime(new Date());
        cart.setDeleted(false);
        //通过goodsId获取goods
        Goods goods = goodsMapper.selectByPrimaryKey(cart.getGoodsId());
        //通过productId获取product
        GoodsProduct goodsProduct = productMapper.selectByPrimaryKey(cart.getProductId());

        cart.setGoodsSn(goods.getGoodsSn());
        cart.setGoodsName(goods.getName());
        Double i = cart.getNumber() * goods.getRetailPrice().doubleValue();
        cart.setPrice(i);
        StringBuilder sb=new StringBuilder();
        for (String specification : goodsProduct.getSpecifications()) {
            sb.append(specification);
        }
        cart.setSpecifications(sb.toString());
        cart.setChecked(true);
        cart.setPicUrl(goods.getPicUrl());
        int id=cartMapper.insert(cart);

        //获取所有订单
        List<Cart> cartList=cartMapper.selectAllCart();
        int number=0;
        for (Cart cart1 : cartList) {
            number+=cart1.getNumber();
        }
        return id;
    }

    @Override
    public void cartUpdate(Cart cart) {
        cartMapper.updateByPrimaryKey(cart);
    }

    @Override
    public Map<String, Object> cartCheckde(CartCondition cartCondition) {
        //将数组中所有的订单的checked改成true
        List<Integer> productIds = cartCondition.getProductIds();
        boolean check=true;
        if(cartCondition.getIsChecked()==0){
            check=false;
        }
        if(productIds!=null){
            for (Integer productId : productIds) {
                cartMapper.updateCheckedByProductId(productId,check);
            }
        }
        //返回所有的订单
        Map<String, Object> map = cartIndex();
        return map;
    }

    @Override
    public Map<String, Object> cartDelete(CartCondition cartCondition) {
        List<Integer> productIds = cartCondition.getProductIds();
        //删除订单
        if(productIds!=null){
            for (Integer productId : productIds) {
                cartMapper.deleteByProductId(productId);
            }
        }
        Map<String, Object> map = cartIndex();
        return map;
    }

    @Autowired
    CollectMapper collectMapper;
    @Override
    public Map<String, Object> collectList(Integer type, Integer page, Integer size) {
        PageHelper.startPage(page,size);
        //通过type获取所有收藏的商品
        List<Collect> list=collectMapper.selectAllCollect();
        PageInfo<Collect> collectPageInfo = new PageInfo<>(list);
        long total = collectPageInfo.getTotal();
        ArrayList<Goods> goods = new ArrayList<>();
        if(list!=null){
            for (Collect collect : list) {
                Goods goods1 = goodsMapper.selectByPrimaryKey(collect.getValueId());
                goods.add(goods1);
            }
        }
        //
        HashMap<String, Object> map = new HashMap<>();
        map.put("totalPages",Math.ceil(1.0*total/size));
        map.put("collectList",goods);
        return map;
    }
}
