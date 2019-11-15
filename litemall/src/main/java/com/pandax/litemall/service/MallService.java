package com.pandax.litemall.service;

import com.pandax.litemall.bean.Brand;
import com.pandax.litemall.bean.Region;

import java.util.HashMap;

public interface MallService {
    public Region[] region();


    HashMap<String, Object> brand(Integer page, Integer limit, String sort,  String order,Integer id,String name);

    Brand brandUpdate(Brand newBrand);

    void brandDelete(Brand newBrand);

    Brand brandCreate(Brand newBrand);
}
