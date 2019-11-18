package com.pandax.litemall.mapper;

import com.pandax.litemall.bean.SystemInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ConfigMapper {

    List<SystemInfo> getMallConfig();

    int updateMallConfig(@Param("mallConfigMap") Map mallConfigMap);

    List<SystemInfo> getExpressConfig();

    int updateExpressConfig(@Param("expressConfigMap") Map expressConfigMap);

    List<SystemInfo> getOrderConfig();

    int updateOrderConfig(@Param("orderConfigMap") Map orderConfigMap);

    List<SystemInfo> getWxConfig();

    int updateWxConfig(@Param("wxConfigMap") Map wxConfigMap);
}
