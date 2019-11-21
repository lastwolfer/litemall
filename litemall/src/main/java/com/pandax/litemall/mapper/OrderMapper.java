package com.pandax.litemall.mapper;

import com.pandax.litemall.bean.HandleOption;
import com.pandax.litemall.bean.Order;
import com.pandax.litemall.bean.OrderExample;
import java.util.List;

import com.pandax.litemall.bean.OrderGoods;
import org.apache.ibatis.annotations.Param;

public interface OrderMapper {
    long countByExample(OrderExample example);

    int deleteByExample(OrderExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    List<Order> selectByExample(OrderExample example);

    Order selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Order record, @Param("example") OrderExample example);

    int updateByExample(@Param("record") Order record, @Param("example") OrderExample example);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    List<Order> selectByCondition(@Param("orderStatusArray") Integer orderStatusArray, @Param("sort") String sort, @Param("order") String order, @Param("userId") Integer userId, @Param("orderSn") String orderSn);

    String getOrderStatusText(@Param("showType") Short showType);

    HandleOption getHandleOption(@Param("statusId") Short showType);

    List<OrderGoods> getOrderGoods(@Param("orderId") Short orderId);

    Integer selectLastInsertId();

    int updateCancel(@Param("orderId") Short orderId);

    int updateDelete(@Param("orderId") Short orderId);

    int updateConfirm(@Param("orderId") Short orderId);
}
