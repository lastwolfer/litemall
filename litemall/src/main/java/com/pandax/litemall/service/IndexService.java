package com.pandax.litemall.service;

import com.pandax.litemall.bean.*;

import java.util.List;

public interface IndexService {
    List<Banner> selectBanner();

    List<Brand> selectBrand();

    Category selectCategoryOne();

    List<Category> selectCategoryByPid(Integer id);

    Category selectCategoryL1ById(Integer id);

    List<Topic> selectTopic();

    List<Ad> selectAd();
}
