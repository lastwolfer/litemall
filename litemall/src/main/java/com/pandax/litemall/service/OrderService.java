package com.pandax.litemall.service;


import com.pandax.litemall.bean.Comment;
import com.pandax.litemall.bean.OrderGoods;
import com.pandax.litemall.bean.OrderSubmitInfo;

import java.util.Map;
import com.pandax.litemall.bean.Order;
import java.util.List;


/**
 * @author Da
 * @version 1.0
 * @date 2019/11/15
 * @time 19:48
 */

public interface OrderService {
    int countOrders();

    Map<String,Object> getOrderList(Short showType, Integer page, Integer size);

    Map<String, Object> getOrderDetail(Integer orderId);

    Integer orderSubmit(OrderSubmitInfo orderSubmitInfo);

    int cancelOrder(Short orderId);

    int deleteOrder(Short orderId);

    int confirmOrder(Short orderId);

    List<Order> selectOrderByUserIdAndStatus(Integer userId, Short status);

    int refundOrder(Integer orderIdX);

    int prePay(Integer orderIdX);

    OrderGoods getOrderGoods(Integer orderId, Integer goodsId);

    int commentPost(Comment comment);
}

