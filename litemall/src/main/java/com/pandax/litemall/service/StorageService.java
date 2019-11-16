package com.pandax.litemall.service;

import com.pandax.litemall.bean.Storage;

import java.util.HashMap;

public interface StorageService {
    HashMap<String, Object> queryStorage(Integer page, Integer limit, String sort, String order,String key,String name);

    Storage updateStorage(Storage storage);

    int deleteStorage(Storage storage);
}
