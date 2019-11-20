package com.pandax.litemall.service;

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

    @Override
    public int countOrders() {
        return (int) orderMapper.countByExample(null);
    }

    @Override
    public List<Order> selectOrderByUserIdAndStatus(Integer userId, Short status) {
        OrderExample orderExample = new OrderExample();
        orderExample.createCriteria().andUserIdEqualTo(userId).andOrderStatusEqualTo(status);
        return null;
    }
}
