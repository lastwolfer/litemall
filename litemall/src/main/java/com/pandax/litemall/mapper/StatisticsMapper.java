package com.pandax.litemall.mapper;

import com.pandax.litemall.bean.StatGoodsDb;
import com.pandax.litemall.bean.StatOrderDb;
import com.pandax.litemall.bean.StatUserDb;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface StatisticsMapper {

    @Select("select date_format(add_time,'%Y-%m-%d') as day,count(id) as users from cskaoyan_mall_user group by day")
    List<StatUserDb> getUserByDay();

    @Select("select sum(actual_price) as amount,count(id) as orders,count(distinct user_id) as customers,date_format(o.pay_time,'%Y-%m-%d') as day from cskaoyan_mall_order o group by day")
    List<StatOrderDb> getOrderStat();

    @Select("select sum(actual_price) as amount,count(id) as orders,sum(products) as products,day from\n" +
            "(select actual_price,o.id,sum(number) as products,date_format(o.pay_time,'%Y-%m-%d') as day from cskaoyan_mall_order o join cskaoyan_mall_order_goods g on o.id = g.order_id group by order_id) t group by day")
    List<StatGoodsDb> getOrderGoodsStat();

}
