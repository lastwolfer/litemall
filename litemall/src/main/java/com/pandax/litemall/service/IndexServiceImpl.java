package com.pandax.litemall.service;

import com.github.pagehelper.PageHelper;
import com.pandax.litemall.bean.Banner;
import com.pandax.litemall.bean.Brand;
import com.pandax.litemall.bean.Category;
import com.pandax.litemall.bean.CategoryExample;
import com.pandax.litemall.mapper.BannerMapper;
import com.pandax.litemall.mapper.BrandMapper;
import com.pandax.litemall.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndexServiceImpl implements IndexService {
    @Autowired
    BannerMapper bannerMapper;
    @Autowired
    BrandMapper brandMapper;
    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public List<Banner> selectBanner() {
        return bannerMapper.selectByExample(null);
    }

    @Override
    public List<Brand> selectBrand() {
        return brandMapper.selectByExample(null);
    }

    @Override
    public Category selectCategoryOne() {
        return categoryMapper.selectOneL1();
    }

    /**
     * 根据L1 id 查询 L2
     * @param id
     * @return
     */
    @Override
    public List<Category> selectCategoryByPid(Integer id) {
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.createCriteria().andPidEqualTo(id);
        return categoryMapper.selectByExample(categoryExample);
    }

    /**
     * 根据Id获取一级分类
     * @param id
     * @return
     */
    @Override
    public Category selectCategoryL1ById(Integer id) {
        return categoryMapper.selectByPrimaryKey(id);
    }
}
