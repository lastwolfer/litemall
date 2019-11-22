package com.pandax.litemall.mapper;

import com.pandax.litemall.bean.HandleOption;
import com.pandax.litemall.bean.Order;
import com.pandax.litemall.bean.OrderExample;
import java.util.List;
import java.util.Map;

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

    String getOrderStatusText(@Param("orderStatus") Short orderStatus);

    HandleOption getHandleOption(@Param("statusId") Short showType);

    List<OrderGoods> getOrderGoods(@Param("orderId") Integer orderId);

    Integer selectLastInsertId();

    int updateCancel(@Param("orderId") Short orderId);

    int updateDelete(@Param("orderId") Short orderId);

    int updateConfirm(@Param("orderId") Short orderId,@Param("comments") Integer comments);

    List<Order> getOrders(@Param("status") Short status,@Param("userId") Integer userId);

    List<Order> getAllOrders(@Param("userId") Integer userId);

    int updateShipInfo(@Param("orderId") Integer orderId,
                       @Param("shipChannel") String shipChannel,
                       @Param("shipSn") String shipSn);

    int refundOrder(@Param("orderId") Integer orderIdX);

    int updatePrePay(@Param("orderId") Integer orderIdX, @Param("payId") String payId);

    int updateOrderComment(Integer orderId);
}
