package com.pandax.litemall.service;

import com.pandax.litemall.bean.Banner;
import com.pandax.litemall.bean.Brand;
import com.pandax.litemall.mapper.BannerMapper;
import com.pandax.litemall.mapper.BrandMapper;
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

    @Override
    public List<Banner> selectBanner() {
        return bannerMapper.selectByExample(null);
    }

    @Override
    public List<Brand> selectBrand() {
        return brandMapper.selectByExample(null);
    }
}
