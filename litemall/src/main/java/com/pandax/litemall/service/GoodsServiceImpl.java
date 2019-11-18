package com.pandax.litemall.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pandax.litemall.bean.*;
import com.pandax.litemall.mapper.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    BrandMapper brandMapper;
    @Autowired
    GoodsSpecificationMapper goodsSpecificationMapper;
    @Autowired
    GoodsProductMapper goodsProductMapper;
    @Autowired
    GoodsAttributeMapper goodsAttributeMapper;
    @Autowired
    CommentMapper commentMapper;

    /**
     * 商品清单分页，排序
     *
     * @return
     */
    @Override
    public List<Goods> goodsList(QuerryGoodsList querryGoodsList) {
        //分页查询
        PageHelper.startPage(querryGoodsList.getPage(), querryGoodsList.getLimit());
        GoodsExample goodsExample = new GoodsExample();
        GoodsExample.Criteria criteria = goodsExample.createCriteria();
        if (querryGoodsList.getGoodsSn() != null && !querryGoodsList.getGoodsSn().equals("".trim())) {
            criteria.andGoodsSnEqualTo(querryGoodsList.getGoodsSn());
        }
        if (querryGoodsList.getName() != null) {
            criteria.andNameLike("%" + querryGoodsList.getName() + "%");
        }
        goodsExample.setOrderByClause(querryGoodsList.getSort() + " " + querryGoodsList.getOrder());
        List<Goods> goodsList = goodsMapper.selectByExampleWithBLOBs(goodsExample);
        return goodsList;
    }

    /**
     * 查询商品分类
     *
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
     *
     * @return
     */
    @Override
    public List QuerryBrand() {
        BrandExample brandExample = new BrandExample();
        brandExample.createCriteria();
        List<Brand> brands = brandMapper.selectByExample(brandExample);
        List<Map<String, Object>> list = new ArrayList<>();
        for (Brand brand : brands) {
            Map<String, Object> map = new HashMap<>();
            map.put("value", brand.getId());
            map.put("label", brand.getName());
            list.add(map);
        }
        return list;

    }

    public int countGoods() {
        return (int) goodsMapper.countByExample(null);
    }

    /**
     * 添加商品
     *
     * @param goodsCreateBean
     * @return
     */
    @Override
    public int createGoods(GoodsCreateBean goodsCreateBean) {
        Date date = new Date();
        Goods goods = goodsCreateBean.getGoods();
        goods.setAddTime(date);
        int insert = goodsMapper.insertSelective(goods);
        List<GoodsSpecification> specifications = goodsCreateBean.getSpecifications();
        for (GoodsSpecification specification : specifications) {
            specification.setGoodsId(goods.getId());
            specification.setAddTime(date);
            goodsSpecificationMapper.insertSelective(specification);
        }

        List<GoodsProduct> products = goodsCreateBean.getProducts();
        for (GoodsProduct product : products) {
            product.setGoodsId(goods.getId());
            product.setId(null);
            product.setAddTime(date);
            goodsProductMapper.insertSelective(product);
        }
        List<GoodsAttribute> attributes = goodsCreateBean.getAttributes();
        for (GoodsAttribute attribute : attributes) {
            attribute.setGoodsId(goods.getId());
            attribute.setAddTime(date);
            goodsAttributeMapper.insertSelective(attribute);
        }
        return insert;
    }

    @Override
    public GoodsCreateBean selectGoodsById(Integer id) {
        GoodsCreateBean goodsCreateBean = new GoodsCreateBean();
        Goods goods = goodsMapper.selectByPrimaryKey(id);
        goodsCreateBean.setGoods(goods);

        //查询分类id
        ArrayList<String> categoryIds = new ArrayList<>();
        Integer categoryId1 = goods.getCategoryId();
        Category category = categoryMapper.selectByPrimaryKey(categoryId1);
        Integer categoryId2 = category.getPid();
        categoryIds.add(String.valueOf(categoryId1));
        categoryIds.add(String.valueOf(categoryId2));
        goodsCreateBean.setCategoryIds(categoryIds);

        //查询attributes 参数
        GoodsAttributeExample goodsAttributeExample = new GoodsAttributeExample();
        goodsAttributeExample.createCriteria().andGoodsIdEqualTo(id).andDeletedNotEqualTo(true);
        List<GoodsAttribute> goodsAttributes = goodsAttributeMapper.selectByExample(goodsAttributeExample);
        goodsCreateBean.setAttributes(goodsAttributes);

        //查询specifications 规格
        GoodsSpecificationExample goodsSpecificationExample = new GoodsSpecificationExample();
        goodsSpecificationExample.createCriteria().andGoodsIdEqualTo(id).andDeletedNotEqualTo(true);
        List<GoodsSpecification> goodsSpecifications = goodsSpecificationMapper.selectByExample(goodsSpecificationExample);
        goodsCreateBean.setSpecifications(goodsSpecifications);

        //查询 products 套餐组合
        GoodsProductExample goodsProductExample = new GoodsProductExample();
        goodsProductExample.createCriteria().andGoodsIdEqualTo(id).andDeletedNotEqualTo(true);
        List<GoodsProduct> goodsProducts = goodsProductMapper.selectByExample(goodsProductExample);
        goodsCreateBean.setProducts(goodsProducts);

        return goodsCreateBean;
    }

    /**
     * 更新商品信息
     *
     * @param goodsCreateBean
     * @return
     */
    @Override
    public int updateGoods(GoodsCreateBean goodsCreateBean) {
        Date date = new Date();
        Goods goods = goodsCreateBean.getGoods();
        Integer goodsId = goods.getId();
        goods.setUpdateTime(date);
        int insert = goodsMapper.updateByPrimaryKeyWithBLOBs(goods);

        deleteGoodsParam(goodsId);

        List<GoodsSpecification> specifications = goodsCreateBean.getSpecifications();
        for (GoodsSpecification specification : specifications) {
            specification.setGoodsId(goodsId);
            specification.setUpdateTime(date);
            specification.setAddTime(date);
            goodsSpecificationMapper.insertSelective(specification);
        }

        List<GoodsProduct> products = goodsCreateBean.getProducts();
        for (GoodsProduct product : products) {
            product.setGoodsId(goodsId);
            product.setId(null);
            product.setAddTime(date);
            product.setUpdateTime(date);
            goodsProductMapper.insertSelective(product);
        }
        List<GoodsAttribute> attributes = goodsCreateBean.getAttributes();
        for (GoodsAttribute attribute : attributes) {
            attribute.setGoodsId(goodsId);
            attribute.setUpdateTime(date);
            attribute.setAddTime(date);
            goodsAttributeMapper.insertSelective(attribute);
        }
        return insert;
    }

    /**
     * 删除商品
     * @param map
     * @return
     */
    @Override
    public int deleteGoods(Map map) {
        Integer id = (Integer) map.get("id");
        deleteGoodsParam(id);
        int i = goodsMapper.deleteByPrimaryKey(id);
        return i;
    }

    @Override
    public List<Comment> commentList(QuerryCommentList querryCommentList) {
        //分页查询
        PageHelper.startPage(querryCommentList.getPage(), querryCommentList.getLimit());
        CommentExample commentExample = new CommentExample();
        CommentExample.Criteria criteria = commentExample.createCriteria();

        if (querryCommentList.getUserId() != null) {
            criteria.andUserIdEqualTo(querryCommentList.getUserId());
        }
        if (querryCommentList.getValueId() != null) {
            criteria.andValueIdEqualTo(querryCommentList.getValueId());
        }

        commentExample.setOrderByClause(querryCommentList.getSort() + " " + querryCommentList.getOrder());
        List<Comment> commentList = commentMapper.selectByExample(commentExample);
        for (Comment comment : commentList) {
            if ( !comment.getHasPicture()) {//如果没有图片则将图片
                comment.setPicUrls(null);
            }
        }
        return commentList;
    }

    /**
     * 删除商品相关信息
     */
    public int deleteGoodsParam(Integer goodsId) {
        GoodsProductExample goodsProductExample = new GoodsProductExample();
        goodsProductExample.createCriteria().andGoodsIdEqualTo(goodsId);
        int i1 = goodsProductMapper.deleteByExample(goodsProductExample);
        GoodsAttributeExample goodsAttributeExample = new GoodsAttributeExample();
        goodsAttributeExample.createCriteria().andGoodsIdEqualTo(goodsId);
        int i2 = goodsAttributeMapper.deleteByExample(goodsAttributeExample);
        GoodsSpecificationExample goodsSpecificationExample = new GoodsSpecificationExample();
        goodsSpecificationExample.createCriteria().andGoodsIdEqualTo(goodsId);
        int i3 = goodsSpecificationMapper.deleteByExample(goodsSpecificationExample);
        return i1 + i2 + i3;
    }

}