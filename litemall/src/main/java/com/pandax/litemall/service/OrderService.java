package com.pandax.litemall.service;

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

    List<Order> selectOrderByUserIdAndStatus(Integer userId, Short status);
}
