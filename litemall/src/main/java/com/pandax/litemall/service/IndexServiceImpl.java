package com.pandax.litemall.service;

import com.github.pagehelper.PageHelper;
import com.pandax.litemall.bean.*;
import com.pandax.litemall.mapper.*;
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
    @Autowired
    TopicMapper topicMapper;
    @Autowired
    AdMapper adMapper;

    @Override
    public List<Banner> selectBanner() {
        return bannerMapper.selectByExample(null);
    }

    @Override
    public List<Brand> selectBrand() {
        PageHelper.startPage(1,4);
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

    /**
     * 查询专题
     * @return
     */
    @Override
    public List<Topic> selectTopic() {
        return topicMapper.getTopic();
    }

    @Override
    public List<Ad> selectAd() {
        AdExample adExample = new AdExample();
        adExample.createCriteria().andEnabledEqualTo(true);
        return adMapper.selectByExample(adExample);
    }
}
