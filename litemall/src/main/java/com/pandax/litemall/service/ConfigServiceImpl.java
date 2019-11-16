package com.pandax.litemall.service;

import com.pandax.litemall.bean.*;
import com.pandax.litemall.mapper.ConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class ConfigServiceImpl implements ConfigService{

    @Autowired
    ConfigMapper configMapper;

    @Override
    public MallConfig getMallConfig() {
        List<SystemInfo> systemInfos = configMapper.getMallConfig();
        MallConfig mallConfig = new MallConfig();
        mallConfig.setLitemall_mall_name(systemInfos.get(0).getKeyValue());
        mallConfig.setLitemall_mall_address(systemInfos.get(3).getKeyValue());
        mallConfig.setLitemall_mall_phone(systemInfos.get(2).getKeyValue());
        mallConfig.setLitemall_mall_qq(systemInfos.get(1).getKeyValue());
        return mallConfig;
    }

    @Override
    public int updateMallConfig(MallConfig mallConfig) {
        Map<String,String> mallConfigMap = new HashMap<>();
        mallConfigMap.put("cskaoyan_mall_mall_address",mallConfig.getLitemall_mall_address());
        mallConfigMap.put("cskaoyan_mall_mall_name",mallConfig.getLitemall_mall_name());
        mallConfigMap.put("cskaoyan_mall_mall_phone",mallConfig.getLitemall_mall_phone());
        mallConfigMap.put("cskaoyan_mall_mall_qq",mallConfig.getLitemall_mall_qq());
        int updateStatus = configMapper.updateMallConfig(mallConfigMap);
        return updateStatus;
    }

    @Override
    public ExpressConfig getExpressConfig() {
        List<SystemInfo> systemInfos = configMapper.getExpressConfig();
        ExpressConfig expressConfig = new ExpressConfig();
        expressConfig.setLitemall_express_freight_min(systemInfos.get(0).getKeyValue());
        expressConfig.setLitemall_express_freight_value(systemInfos.get(1).getKeyValue());
        return expressConfig;
    }

    @Override
    public int updateExpressConfig(ExpressConfig expressConfig) {
        Map<String,String> expressConfigMap = new HashMap<>();
        expressConfigMap.put("cskaoyan_mall_express_freight_min", expressConfig.getLitemall_express_freight_min());
        expressConfigMap.put("cskaoyan_mall_express_freight_value", expressConfig.getLitemall_express_freight_value());
        int updateStatus = configMapper.updateExpressConfig(expressConfigMap);
        return updateStatus;
    }

    @Override
    public OrderConfig getOrderConfig() {
        List<SystemInfo> systemInfos = configMapper.getOrderConfig();
        OrderConfig orderConfig = new OrderConfig();
        orderConfig.setLitemall_order_comment(systemInfos.get(2).getKeyValue());
        orderConfig.setLitemall_order_unpaid(systemInfos.get(0).getKeyValue());
        orderConfig.setLitemall_order_unconfirm(systemInfos.get(1).getKeyValue());
        return orderConfig;
    }

    @Override
    public int updateOrderConfig(OrderConfig orderConfig) {
        Map<String,String> orderConfigMap = new HashMap<>();
        orderConfigMap.put("cskaoyan_mall_order_comment", orderConfig.getLitemall_order_comment());
        orderConfigMap.put("cskaoyan_mall_order_unpaid", orderConfig.getLitemall_order_unpaid());
        orderConfigMap.put("cskaoyan_mall_order_unconfirm", orderConfig.getLitemall_order_unconfirm());
        int updateStatus = configMapper.updateOrderConfig(orderConfigMap);
        return updateStatus;
    }

    @Override
    public WxConfig getWxConfig() {
        List<SystemInfo> systemInfos = configMapper.getWxConfig();
        WxConfig wxConfig = new WxConfig();
        for (SystemInfo systemInfo : systemInfos) {
            systemInfo.setKeyName(systemInfo.getKeyName().replace("cskaoyan_mall", "litemall"));
        }
        for (SystemInfo systemInfo : systemInfos) {
            if(systemInfo.getKeyName().equals("litemall_wx_index_new")){
                wxConfig.setLitemall_wx_index_new(systemInfo.getKeyValue());
            }else if(systemInfo.getKeyName().equals("litemall_wx_catlog_goods")){
                wxConfig.setLitemall_wx_catlog_goods(systemInfo.getKeyValue());
            }else if(systemInfo.getKeyName().equals("litemall_wx_catlog_list")){
                wxConfig.setLitemall_wx_catlog_list(systemInfo.getKeyValue());
            }else if(systemInfo.getKeyName().equals("litemall_wx_share")){
                wxConfig.setLitemall_wx_share(systemInfo.getKeyValue());
            }else if(systemInfo.getKeyName().equals("litemall_wx_index_brand")){
                wxConfig.setLitemall_wx_index_brand(systemInfo.getKeyValue());
            }else if(systemInfo.getKeyName().equals("litemall_wx_index_hot")){
                wxConfig.setLitemall_wx_index_hot(systemInfo.getKeyValue());
            }else{
                wxConfig.setLitemall_wx_index_topic(systemInfo.getKeyValue());
            }
        }
        return wxConfig;
    }

    @Override
    public int updateWxConfig(WxConfig wxConfig) {
        Map<String,String> wxConfigMap = new HashMap<>();
        wxConfigMap.put("cskaoyan_mall_wx_index_new", wxConfig.getLitemall_wx_index_new());
        wxConfigMap.put("cskaoyan_mall_wx_catlog_goods", wxConfig.getLitemall_wx_catlog_goods());
        wxConfigMap.put("cskaoyan_mall_wx_catlog_list", wxConfig.getLitemall_wx_catlog_list());
        wxConfigMap.put("cskaoyan_mall_wx_share", wxConfig.getLitemall_wx_share());
        wxConfigMap.put("cskaoyan_mall_wx_index_brand", wxConfig.getLitemall_wx_index_brand());
        wxConfigMap.put("cskaoyan_mall_wx_index_hot", wxConfig.getLitemall_wx_index_hot());
        wxConfigMap.put("cskaoyan_mall_wx_index_topic", wxConfig.getLitemall_wx_index_topic());
        int updateStatus = configMapper.updateWxConfig(wxConfigMap);
        return updateStatus;
    }
}
