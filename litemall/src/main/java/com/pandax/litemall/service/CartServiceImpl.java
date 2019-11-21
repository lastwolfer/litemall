package com.pandax.litemall.service;
import java.util.Date;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pandax.litemall.bean.*;
import com.pandax.litemall.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CartServiceImpl implements CartService{

    @Autowired
    CartMapper cartMapper;
    @Override
    public Map<String, Object> cartIndex(User user) {
        CartTotal cartTotal = new CartTotal();

        //获取所有的Cart
        List<Cart> cartList=cartMapper.selectAllCart(user.getId());
        cartTotal.setGoodsCount(cartList.size());
        cartTotal.setCheckedGoodsCount(cartList.size());
        Double sum=0.0;
        if(cartList!=null){
            for (Cart cart : cartList) {
                if(cart.getChecked()){
                    sum=sum+cart.getPrice().doubleValue()*cart.getNumber();
                    System.out.println(cart.getPrice());
                }
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
    public int cartAdd(Cart cart, User user) {
        cart.setUserId(user.getId());
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
        List<Cart> cartList=cartMapper.selectAllCart(user.getId());
        int number=0;
        for (Cart cart1 : cartList) {
            number+=cart1.getNumber();
        }
        return number;
    }

    @Override
    public int cartFastadd(Cart cart, User user) {
        cart.setUserId(user.getId());
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
        List<Cart> cartList=cartMapper.selectAllCart(user.getId());
        int number=0;
        for (Cart cart1 : cartList) {
            number+=cart1.getNumber();
        }
        return id;
    }

    @Override
    public void cartUpdate(Cart cart, User user) {
        cart.setUserId(user.getId());
        cartMapper.updateNumber(cart.getId(),cart.getNumber());
    }

    @Override
    public Map<String, Object> cartCheckde(CartCondition cartCondition, User user) {
        //将数组中所有的订单的checked改成true
        List<Integer> productIds = cartCondition.getProductIds();
        boolean check=true;
        if(cartCondition.getIsChecked()==0){
            check=false;
        }
        if(productIds!=null){
            for (Integer productId : productIds) {
                cartMapper.updateCheckedByProductId(productId,check,user.getId());
            }
        }
        //返回所有的订单
        Map<String, Object> map = cartIndex(user);
        return map;
    }

    @Override
    public Map<String, Object> cartDelete(CartCondition cartCondition, User user) {
        List<Integer> productIds = cartCondition.getProductIds();
        //删除订单
        if(productIds!=null){
            for (Integer productId : productIds) {
                cartMapper.deleteByProductId(productId,user.getId());
            }
        }
        Map<String, Object> map = cartIndex(user);
        return map;
    }

    @Autowired
    CollectMapper collectMapper;
    @Override
    public Map<String, Object> collectList(Integer type, Integer page, Integer size, User user) {
        PageHelper.startPage(page,size);
        //通过type获取所有收藏的商品
        List<Collect> list=collectMapper.selectAllCollect(user.getId());
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

    @Override
    public Map<String, Object> collectAddOrDelete(Map<String, Integer> map, User user) {
        //首先去collect表中查询看是否存在表中
        Integer id=collectMapper.selectIdByValueId(map.get("valueId"),user.getId());
        //如果没有找到说明还未被收藏需要收藏一下
        HashMap<String, Object> map1 = new HashMap<>();
        if(id==null){
            Collect collect = new Collect();
            collect.setUserId(user.getId());
            collect.setValueId(map.get("valueId"));
            collect.setType((byte)0);
            collect.setAddTime(new Date());
            collect.setUpdateTime(new Date());
            collect.setDeleted(false);
            collectMapper.insert(collect);
            map1.put("type","add");
        }else{
            //如果找到说明已经被收藏应该被删除
            collectMapper.deleteByPrimaryKey(id);
            map1.put("type","delete");
        }
        return map1;
    }

    @Autowired
    OrderGoodsMapper orderGoodsMapper;
    @Autowired
    AddressMapper addressMapper;
    @Override
    public CartCheckedCondition cartCheckout(Integer cartId, Integer addressId, Integer couponId, Integer grouponRulesId, User user) {
        Map<String, Object> map = cartIndex(user);
        CartCheckedCondition cartCheckedCondition = new CartCheckedCondition();
        List<Cart> cartList = (List<Cart>) map.get("cartList");
        List<Cart>cartList1=new ArrayList<>();
        for (Cart cart : cartList) {
            if(cart.getChecked()){
                cartList1.add(cart);
            }
        }
        //查询地址
        Address address = addressMapper.selectByPrimaryKey(addressId);
        cartCheckedCondition.setCheckedAddress(address);
        cartCheckedCondition.setAddressId(addressId);
        cartCheckedCondition.setCheckedGoodsList(cartList1);
        CartTotal cartTotal = (CartTotal) map.get("cartTotal");
        Double checkedGoodsAmount = (Double) cartTotal.getCheckedGoodsAmount();
        cartCheckedCondition.setActualPrice(checkedGoodsAmount);
        cartCheckedCondition.setOrderTotalPrice(checkedGoodsAmount);
        cartCheckedCondition.setGoodsTotalPrice(checkedGoodsAmount);
        return cartCheckedCondition;
    }

    @Override
    public Integer cartGoodscount(User user) {
        Map<String, Object> stringObjectMap = cartIndex(user);
        List<Cart> cartList = (List<Cart>) stringObjectMap.get("cartList");
        int number=0;
        if(cartList!=null){
            for (Cart cart : cartList) {
                if(cart.getNumber()!=0){
                    number+=cart.getNumber();
                }
            }
        }
        return number;
    }
}
