package com.pandax.litemall.service;

import com.pandax.litemall.bean.MallConfig;
import com.pandax.litemall.bean.ExpressConfig;
import com.pandax.litemall.bean.OrderConfig;
import com.pandax.litemall.bean.WxConfig;

public interface ConfigService {

    MallConfig getMallConfig();

    int updateMallConfig(MallConfig mallConfig);

    ExpressConfig getExpressConfig();

    int updateExpressConfig(ExpressConfig expressConfig);

    OrderConfig getOrderConfig();

    int updateOrderConfig(OrderConfig orderConfig);

    WxConfig getWxConfig();

    int updateWxConfig(WxConfig wxConfig);
}
