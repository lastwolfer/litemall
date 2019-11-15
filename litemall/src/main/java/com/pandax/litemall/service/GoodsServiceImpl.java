package com.pandax.litemall.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pandax.litemall.bean.*;
import com.pandax.litemall.mapper.BrandMapper;
import com.pandax.litemall.mapper.CategoryMapper;
import com.pandax.litemall.mapper.GoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    BrandMapper brandMapper;

    /**
     * 商品清单分页，排序
     * @return
     */
    @Override
    public List<Goods> goodsList(QuerryGoodsList querryGoodsList) {
        //分页查询
        PageHelper.startPage(querryGoodsList.getPage(),querryGoodsList.getLimit());
        GoodsExample goodsExample = new GoodsExample();
        GoodsExample.Criteria criteria = goodsExample.createCriteria();
        if (querryGoodsList.getGoodsSn() != null && !querryGoodsList.getGoodsSn().equals("".trim())){
            criteria.andGoodsSnEqualTo(querryGoodsList.getGoodsSn());
        }
        if (querryGoodsList.getName()!=null){
            criteria.andNameLike("%"+querryGoodsList.getName()+"%");
        }
        goodsExample.setOrderByClause(querryGoodsList.getSort()+" "+querryGoodsList.getOrder());
        List<Goods> goodsList = goodsMapper.selectByExample(goodsExample);
        return goodsList;
    }

    /**
     * 查询商品分类
     * @return
     */
    @Override
    public List<CategoryList> QuerryCat() {
        //查询商品总分类
        List<CategoryList> categoryList = goodsMapper.selectCategory(0);
        for (CategoryList category : categoryList) {
            //查询商品二级分类
            List<CategoryList> childrenList = goodsMapper.selectCategory(category.getValue());
            category.setChildren(childrenList);
        }
        return categoryList;
    }

    /**
     * 查询制造商
     * @return
     */
    @Override
    public List QuerryBrand() {
        BrandExample brandExample = new BrandExample();
        brandExample.createCriteria();
        List<Brand> brands = brandMapper.selectByExample(brandExample);
       List<Map<String,Object>> list = new ArrayList<>();
        for (Brand brand : brands) {
            Map<String, Object> map = new HashMap<>();
            map.put("value",brand.getId());
            map.put("label",brand.getName());
            list.add(map);
        }
        return list;
    }
}
