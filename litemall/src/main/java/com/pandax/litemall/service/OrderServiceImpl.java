package com.pandax.litemall.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pandax.litemall.bean.*;
import com.pandax.litemall.mapper.*;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.*;

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

    @Override
    public Map<String,Object> getOrderList(Short showType, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        /*查询订单信息*/
        OrderExample example = new OrderExample();
        OrderGoodsExample orderGoodsExample = new OrderGoodsExample();
        GrouponExample grouponExample = new GrouponExample();
        OrderExample.Criteria criteria = example.createCriteria();
        criteria.andOrderStatusEqualTo(showType);
        List<Order> orders = orderMapper.selectByExample(example);
        String orderStatusText = orderMapper.getOrderStatusText(showType);
        HandleOption handleOption = orderMapper.getHandleOption(showType);
        for (Order order : orders) {
            Integer orderId = order.getId();
            /*查询订单的商品列表*/
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
    public Map<String, Object> getOrderDetail(Short orderId) {
        /*获取基本订单信息*/
        Map<String,Object> dataMap = new HashMap<>();
        OrderExample orderExample = new OrderExample();
        orderExample.createCriteria().andOrderStatusEqualTo(orderId);
        Order order = orderMapper.selectByExample(orderExample).get(0);
        /*添加订单可操作状态*/
        HandleOption handleOption = orderMapper.getHandleOption(order.getOrderStatus());
        order.setHandleOption(handleOption);
        /*添加订单商品信息*/
        List<OrderGoods> orderGoodsList = orderMapper.getOrderGoods(orderId);
        GoodsSpecificationExample goodsSpecificationExample = new GoodsSpecificationExample();
        for (OrderGoods orderGoods : orderGoodsList) {
            goodsSpecificationExample.createCriteria().andGoodsIdEqualTo(orderGoods.getGoodsId());
            List<GoodsSpecification> goodsSpecifications = goodsSpecificationMapper.selectByExample(goodsSpecificationExample);
            List<String> specificationsList = new ArrayList<>();
            for (GoodsSpecification goodsSpecification : goodsSpecifications) {
                specificationsList.add(goodsSpecification.getSpecification());
            }
            orderGoods.setSpecifications(specificationsList);
        }
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
        Cart cartX = cartList.get(0);
        /*通过addressId获取Address对象*/
        AddressExample addressExample = new AddressExample();
        addressExample.createCriteria().andAddressEqualTo(addressId.toString());
        Address address = addressMapper.selectByExample(addressExample).get(0);
        /*拼接订单基本信息*/
        Order order = new Order();
        //order.setOrderSn
        order.setOrderStatus((short) 101);
        order.setMessage(message);
        /*拼接订单用户地址信息*/
        order.setUserId(cartX.getUserId());
        order.setConsignee(address.getName());
        order.setMobile(address.getMobile());
        order.setAddress(address.getAddress());
        /*拼接订单价格信息*/
        BigDecimal goodsPrice = BigDecimal.valueOf(0);
        BigDecimal productPrice;
        Short number;
        for (Cart cart : cartList) {
            productPrice = cart.getPrice();
            number = cart.getNumber();
            goodsPrice.add(productPrice.multiply(BigDecimal.valueOf(number)));
        }
        order.setGoodsPrice(goodsPrice);
        order.setFreightPrice(new BigDecimal(0));
        Coupon coupon = couponMapper.selectByPrimaryKey(couponId);
        order.setCouponPrice(coupon.getDiscount());
        order.setIntegralPrice(new BigDecimal(0));
        Groupon groupon = grouponMapper.selectByPrimaryKey(grouponLinkId);
        GrouponRules grouponRules = grouponRulesMapper.selectByPrimaryKey(grouponRulesId);
        order.setGrouponPrice(grouponRules.getDiscount());
        BigDecimal orderPrice = goodsPrice.add(order.getFreightPrice()).subtract(order.getCouponPrice());
        order.setOrderPrice(orderPrice);
        BigDecimal actualPrice = orderPrice.subtract(order.getIntegralPrice());
        order.setActualPrice(actualPrice);
        /*拼接支付信息---待定*/
        /*拼接其他信息*/
        order.setComments((short) 0);
        order.setAddTime(new Date());
        order.setUpdateTime(new Date());
        int insert = orderMapper.insert(order);
        Integer orderId = orderMapper.selectLastInsertId();
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
        int confirmStatus = orderMapper.updateConfirm(orderId);

        return confirmStatus;
    }


}
