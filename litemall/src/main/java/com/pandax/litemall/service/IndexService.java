package com.pandax.litemall.service;

import com.pandax.litemall.bean.Banner;
import com.pandax.litemall.bean.Brand;

import java.util.List;

public interface IndexService {
    List<Banner> selectBanner();

    List<Brand> selectBrand();

}
