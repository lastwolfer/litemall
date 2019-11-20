package com.pandax.litemall.service;

import com.pandax.litemall.bean.Banner;
import com.pandax.litemall.bean.Brand;
import com.pandax.litemall.bean.Category;

import java.util.List;

public interface IndexService {
    List<Banner> selectBanner();

    List<Brand> selectBrand();

    Category selectCategoryOne();

    List<Category> selectCategoryByPid(Integer id);

    Category selectCategoryL1ById(Integer id);
}
