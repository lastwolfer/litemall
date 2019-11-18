package com.pandax.litemall.service;

import com.pandax.litemall.bean.Log;

import java.util.List;
import java.util.Map;

/**
 * @author Da
 * @version 1.0
 * @date 2019/11/16
 * @time 16:55
 */

public interface LogService {
    void record(Log log);

    Map<String, Object> showLogList(Integer page, Integer limit,
                    String sort, String order,  String username);
}
