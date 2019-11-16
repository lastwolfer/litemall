package com.pandax.litemall.service;

import com.pandax.litemall.bean.Storage;
import java.util.HashMap;

/**
 * @author Da
 * @version 1.0
 * @date 2019/11/16
 * @time 14:05
 */

public interface StorageService {

    int saveStorage(Storage storage);

    HashMap<String, Object> queryStorage(Integer page, Integer limit, String sort, String order,String key,String name);

    Storage updateStorage(Storage storage);

    int deleteStorage(Storage storage);
}
