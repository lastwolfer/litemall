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
    @Autowired
    CommentReplyMapper commentReplyMapper;
    @Autowired
    GrouponMapper grouponMapper;
    @Autowired
    GrouponRulesMapper  grouponRulesMapper;

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

    /**
     * 查询商品信息
     *
     * @param id
     * @return
     */
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
     *
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
        criteria.andDeletedNotEqualTo(true);
        if (querryCommentList.getUserId() != null) {
            criteria.andUserIdEqualTo(querryCommentList.getUserId());
        }
        if (querryCommentList.getValueId() != null) {
            criteria.andValueIdEqualTo(querryCommentList.getValueId());
        }

        commentExample.setOrderByClause(querryCommentList.getSort() + " " + querryCommentList.getOrder());
        List<Comment> commentList = commentMapper.selectByExample(commentExample);
        for (Comment comment : commentList) {
            if (!comment.getHasPicture()) {//如果没有图片则将图片
                comment.setPicUrls(null);
            }
        }
        return commentList;
    }

    /**
     * 评论回复 返回0表示已经回复过，返回1表示回复成功
     *
     * @param commentReply
     * @return
     */
    @Override
    public int reply(CommentReply commentReply) {
        Integer i = commentReplyMapper.selectCountByCommentId(commentReply.getCommentId());
        System.out.println(i);
        if (i != 0) {//已经回复
            return 0;
        }
        commentReply.setAddTime(new Date());
        commentReply.setDeleted(false);
        int insert = commentReplyMapper.insert(commentReply);
        return insert;
    }

    /**
     * 删除评论
     *
     * @param comment
     * @return
     */
    @Override
    public int deleteComment(Comment comment) {
        comment.setUpdateTime(new Date());
        comment.setDeleted(true);
        int update1 = commentMapper.updateByPrimaryKeySelective(comment);
        CommentReply commentReply = commentReplyMapper.selectReply(comment.getId());
        //判断是否有回复
        if (commentReply != null) {
            commentReply.setUpdateTime(new Date());
            commentReply.setDeleted(true);
            int update2 = commentReplyMapper.updateByPrimaryKeySelective(commentReply);
        }
        return update1;
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


    /**
     * 查询所有的商品数目
     * @return 封装到map中商品数目
     */
    @Override
    public Map goodsCount() {
        long count = goodsMapper.countByExample(null);
        Map<String,Long> map = new HashMap<>();
        map.put("goodsCount",count);
        return map;
    }

    /**
     查询商品：
     * currentCategory:Object,
     *  brotherCategory:Array,
     *  parentCategory:Object
     * @param id 当前商品id
     * @return 查询结果
     */
    @Override
    public Map selectCategoryByGoodsId(Integer id) {
        //先查询当前商品：currentCategory
        Category currentCategory = categoryMapper.selectByPrimaryKey(id);
        //查询父类商品：parentCategory
        Category parentCategory = categoryMapper.selectByPrimaryKey(currentCategory.getPid());
        //查询所有同类产品：brotherCategory（包括自己）
        CategoryExample example = new CategoryExample();
        CategoryExample.Criteria criteria = example.createCriteria();
        criteria.andPidEqualTo(parentCategory.getId());
        List<Category> brotherCategory = categoryMapper.selectByExample(example);
        Map<String,Object> map = new HashMap<>();
        map.put("currentCategory",currentCategory);
        map.put("brotherCategory",brotherCategory);
        map.put("parentCategory",parentCategory);
        return map;
    }

    /**
     * json:
     *  "specificationList":Array[1],
     *         "groupon":Array[0],
     *         "issue":Array[12],
     *         "userHasCollect":1,
     *         "comment":Object{...},
     *         "attribute":Array[0],
     *         "brand":Object{...},
     *         "productList":Array[1],
     *         "info":Object{...}
     * @param id
     * @return
     */
    @Override
    public Map selectGoodsDetailByGoodsId(Integer id) {
        //查询商品的所有规格:pecificationList
        GoodsSpecificationExample example = new GoodsSpecificationExample();
        example.createCriteria().andGoodsIdEqualTo(id);
        List<GoodsSpecification> goodsSpecifications = goodsSpecificationMapper.selectByExample(example);
        //查看团购：groupon
        //先查询GrouponRules
        GrouponRulesExample grouponRulesExample = new GrouponRulesExample();
        grouponRulesExample.createCriteria().andGoodsIdEqualTo(id);
        List<GrouponRules> grouponRules = grouponRulesMapper.selectByExample(grouponRulesExample);
        //根据GrouponRules中的groupid查询所有Groupon
        List<Groupon> groupons = null;
        for (GrouponRules grouponRule : grouponRules) {
            GrouponExample grouponExample = new GrouponExample();
            grouponExample.createCriteria().andRulesIdEqualTo(grouponRule.getId());
            List<Groupon> groupons1 = grouponMapper.selectByExample(grouponExample);
        }

        //查询issue

        //查看用户收藏：userHasCollect
        //查看评论：comment
        //查询attribute
        GoodsAttributeExample attributeExample = new GoodsAttributeExample();
        attributeExample.createCriteria().andGoodsIdEqualTo(id);
        List<GoodsAttribute> goodsAttributes = goodsAttributeMapper.selectByExample(attributeExample);

        Map<String,Object> map = new HashMap<>();
        map.put("goodsSpecifications",goodsSpecifications);

        return null;
    }

}
