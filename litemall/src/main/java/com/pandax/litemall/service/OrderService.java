package com.pandax.litemall.service;

import com.pandax.litemall.bean.OrderSubmitInfo;

import java.util.Map;

/**
 * @author Da
 * @version 1.0
 * @date 2019/11/15
 * @time 19:48
 */

public interface OrderService {
    int countOrders();

    Map<String,Object> getOrderList(Short showType, Integer page, Integer size);

    Map<String, Object> getOrderDetail(Short orderId);

    Integer orderSubmit(OrderSubmitInfo orderSubmitInfo);

    int cancelOrder(Short orderId);

    int deleteOrder(Short orderId);

    int confirmOrder(Short orderId);
}
