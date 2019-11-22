package com.pandax.litemall.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pandax.litemall.bean.*;
import com.pandax.litemall.handler.String2ArrayTypeHandler;
import com.pandax.litemall.mapper.*;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.*;
import com.pandax.litemall.bean.Order;
import com.pandax.litemall.bean.OrderExample;
import com.pandax.litemall.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Da
 * @version 1.0
 * @date 2019/11/15
 * @time 17:28
 */

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    OrderMapper orderMapper;
    @Autowired
    OrderGoodsMapper orderGoodsMapper;
    @Autowired
    GoodsSpecificationMapper goodsSpecificationMapper;
    @Autowired
    CartMapper cartMapper;
    @Autowired
    AddressMapper addressMapper;
    @Autowired
    CouponMapper couponMapper;
    @Autowired
    GrouponMapper grouponMapper;
    @Autowired
    GrouponRulesMapper grouponRulesMapper;

    @Override
    public int countOrders() {
        return (int) orderMapper.countByExample(null);
    }

    public List<Order> selectOrderByUserIdAndStatus(Integer userId, Short status) {
        OrderExample orderExample = new OrderExample();
        orderExample.createCriteria().andUserIdEqualTo(userId).andOrderStatusEqualTo(status);
        return orderMapper.selectByExample(orderExample);
    }

    @Override
    public Map<String,Object> getOrderList(Short showType, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        /*查询订单信息*/
        OrderExample example = new OrderExample();
        GrouponExample grouponExample = new GrouponExample();
//        OrderExample.Criteria criteria = example.createCriteria();
        List<Short> statusList = new ArrayList<>();
        List<Order> orders = new ArrayList<>();
        if(showType == 0) {
            orders = orderMapper.getAllOrders();
        }else {
            if(showType == 1){
                statusList.add((short) 101);
            }else if(showType == 2){
                statusList.add((short) 201);
                statusList.add((short) 202);
            }else if(showType == 3){
                statusList.add((short) 301);
            }else if(showType == 4){
                statusList.add((short)401);
                statusList.add((short)402);
            }
            for (Short status : statusList) {
                List<Order> sOrderList = orderMapper.getOrders(status);
                orders.addAll(sOrderList);
            }
        }
        HandleOption handleOption = orderMapper.getHandleOption(showType);
        for (Order order : orders) {
            Integer orderId = order.getId();
            /*查询订单的商品列表*/
            OrderGoodsExample orderGoodsExample = new OrderGoodsExample();
            orderGoodsExample.createCriteria().andOrderIdEqualTo(orderId);
            List<OrderGoods> orderGoodsList = orderGoodsMapper.selectByExample(orderGoodsExample);
            order.setGoodsList(orderGoodsList);
            /*查询团购信息*/
            grouponExample.createCriteria().andOrderIdEqualTo(orderId);
            List<Groupon> groupons = grouponMapper.selectByExample(grouponExample);
            if(groupons!=null){
                order.setGroupin(true);
            }else{
                order.setGroupin(false);
            }
            /*设置订单状态信息*/
            String orderStatusText = orderMapper.getOrderStatusText(order.getOrderStatus());
            order.setOrderStatusText(orderStatusText);
            /*设置订单可操作状态*/
            order.setHandleOption(handleOption);
        }
        PageInfo<Order> orderPageInfo = new PageInfo<>(orders);
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("data",orders);
        dataMap.put("count", orderPageInfo.getTotal());
        dataMap.put("totalPages", Math.ceil(orderPageInfo.getTotal()/size));
        return dataMap;
    }

    @Override
    public Map<String, Object> getOrderDetail(Integer orderId) {
        /*获取基本订单信息*/
        Map<String,Object> dataMap = new HashMap<>();
        Order order = orderMapper.selectByPrimaryKey(orderId);
        String orderStatusText = orderMapper.getOrderStatusText(order.getOrderStatus());
        order.setOrderStatusText(orderStatusText);
        /*添加订单可操作状态*/
        HandleOption handleOption = orderMapper.getHandleOption(order.getOrderStatus());
        order.setHandleOption(handleOption);
        /*添加订单商品信息*/
        List<OrderGoods> orderGoodsList = orderMapper.getOrderGoods(orderId);
        dataMap.put("orderInfo", order);
        dataMap.put("orderGoods", orderGoodsList);
        return dataMap;
    }

    @Override
    public Integer orderSubmit(OrderSubmitInfo orderSubmitInfo) {
        /*获取请求bean中的信息*/
        Integer addressId = orderSubmitInfo.getAddressId();
        Integer cartId = orderSubmitInfo.getCartId();
        Integer couponId = orderSubmitInfo.getCouponId();
        Integer grouponLinkId = orderSubmitInfo.getGrouponLinkId();
        Integer grouponRulesId = orderSubmitInfo.getGrouponRulesId();
        String message = orderSubmitInfo.getMessage();
        /*通过cartId获取cart信息*/
        CartExample cartExample = new CartExample();
        if(cartId == 0) {
            cartExample.createCriteria().andCheckedEqualTo(true);
        }else{
            cartExample.createCriteria().andIdEqualTo(cartId);
        }
        List<Cart> cartList = cartMapper.selectByExample(cartExample);
        System.out.println(cartList);
        Cart cartX = cartList.get(0);
        /*拼接订单基本信息*/
        Order order = new Order();
        //先给一个orderSn，之后根据订单号补充
        order.setOrderSn("0");
        order.setOrderStatus((short) 101);
        order.setMessage(message);
        /*拼接订单用户地址信息*/
        Address address = addressMapper.selectByPrimaryKey(addressId);
        order.setUserId(cartX.getUserId());
        order.setConsignee(address.getName());
        order.setMobile(address.getMobile());
        order.setAddress(address.getAddress());
        /*拼接订单价格信息*/
        BigDecimal goodsPrice = BigDecimal.valueOf(0);
        BigDecimal productPrice;
        Short number;
        for (Cart cart : cartList) {
            productPrice = new BigDecimal(cart.getPrice());
            number = cart.getNumber();
            BigDecimal multiply = productPrice.multiply(new BigDecimal(Integer.valueOf(number)));
            goodsPrice = goodsPrice.add(multiply);
        }
        order.setGoodsPrice(goodsPrice);
        order.setFreightPrice(new BigDecimal(0));
//        Coupon coupon = couponMapper.selectByPrimaryKey(couponId);
//        order.setCouponPrice(coupon.getDiscount());
        order.setCouponPrice(new BigDecimal("0"));
        order.setIntegralPrice(new BigDecimal(0));
//        Groupon groupon = grouponMapper.selectByPrimaryKey(grouponLinkId);
//        GrouponRules grouponRules = grouponRulesMapper.selectByPrimaryKey(grouponRulesId);
//        order.setGrouponPrice(grouponRules.getDiscount());
        order.setGrouponPrice(new BigDecimal("0"));
        BigDecimal orderPrice = goodsPrice.add(order.getFreightPrice()).subtract(order.getCouponPrice());
        order.setOrderPrice(orderPrice);
        BigDecimal actualPrice = orderPrice.subtract(order.getIntegralPrice());
        order.setActualPrice(actualPrice);
        /*拼接支付信息---待定*/
        /*拼接其他信息*/
        order.setComments((short) 0);
        order.setAddTime(new Date());
        order.setUpdateTime(new Date());
        order.setDeleted(false);
        int insert = orderMapper.insert(order);
        Integer orderId = orderMapper.selectLastInsertId();
        String orderSn = String.valueOf(orderId) + cartX.getUserId() + addressId + cartId;
        order.setOrderSn(orderSn);
        order.setId(orderId);
        orderMapper.updateByPrimaryKeySelective(order);
        /*把orderGoods数据传入数据库*/
        for (Cart cart : cartList) {
            OrderGoods orderGoods = new OrderGoods();
            orderGoods.setOrderId(orderId);
            orderGoods.setGoodsId(cart.getGoodsId());
            orderGoods.setGoodsName(cart.getGoodsName());
            orderGoods.setGoodsSn(cart.getGoodsSn());
            orderGoods.setProductId(cart.getProductId());
            orderGoods.setNumber(cart.getNumber());
            orderGoods.setPrice(new BigDecimal(cart.getPrice()));
            orderGoods.setSpecifications(cart.getSpecifications());
            orderGoods.setPicUrl(cart.getPicUrl());
            orderGoods.setComment(0);
            orderGoods.setAddTime(new Date());
            orderGoods.setUpdateTime(new Date());
            orderGoods.setDeleted(false);
            orderGoodsMapper.insert(orderGoods);
        }
        /*把对应cartId的deleted改为1*/
        for (Cart cart : cartList) {
           int cartDeletedStatus = cartMapper.updateDeleted(cart.getId());
        }
        /*把使用了的coupon券的deleted改为1*/
        int couponDeletedStatus = couponMapper.updateDeleted(couponId);

        if(insert != -1){
            return orderId;
        }
        return null;
    }

    @Override
    public int cancelOrder(Short orderId) {
        int cancelStatus = orderMapper.updateCancel(orderId);
        return cancelStatus;
    }

    @Override
    public int deleteOrder(Short orderId) {
        int deleteStatus = orderMapper.updateDelete(orderId);
        return deleteStatus;
    }

    @Override
    public int confirmOrder(Short orderId) {
        List<OrderGoods> orderGoods = orderMapper.getOrderGoods(Integer.valueOf(orderId));
        Integer comments = orderGoods.size();
        int confirmStatus = orderMapper.updateConfirm(orderId,comments);
        return confirmStatus;
    }

    @Override
    public int refundOrder(Integer orderIdX) {
        int refundStatus = orderMapper.refundOrder(orderIdX);
        return refundStatus;
    }

    @Override
    public int prePay(Integer orderIdX) {
        String payId = String.valueOf(orderIdX + 2019);
        int prePayStatus = orderMapper.updatePrePay(orderIdX,payId);
        return prePayStatus;
    }


}
