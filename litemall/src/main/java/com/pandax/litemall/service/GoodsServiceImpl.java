package com.pandax.litemall.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pandax.litemall.bean.*;
import com.pandax.litemall.mapper.*;

import com.pandax.reponseJson.Comments;
import com.pandax.reponseJson.GoodsDetail;
import com.pandax.reponseJson.SingleComment;
import com.pandax.reponseJson.SpecificationList;
import org.omg.CORBA.OBJ_ADAPTER;
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
    GrouponRulesMapper grouponRulesMapper;
    @Autowired
    IssueMapper issueMapper;
    @Autowired
    CollectMapper collectMapper;
    @Autowired
    HistoryMapper historyMapper;
    @Autowired
    FootprintMapper footprintMapper;
    @Autowired
    UserMapper userMapper;

    /**
     * 商品清单分页，排序
     *
     * @return
     */
    @Override
    public List<Goods> goodsList(QuerryGoodsList querryGoodsList) {
        //分页查询
        Integer limit = querryGoodsList.getLimit() == null ? querryGoodsList.getSize() : querryGoodsList.getLimit();
        PageHelper.startPage(querryGoodsList.getPage(), limit);
        GoodsExample goodsExample = new GoodsExample();
        GoodsExample.Criteria criteria = goodsExample.createCriteria();
        if (querryGoodsList.getGoodsSn() != null && !querryGoodsList.getGoodsSn().equals("".trim())) {
            criteria.andGoodsSnEqualTo(querryGoodsList.getGoodsSn());
        }
        if (querryGoodsList.getName() != null) {
            criteria.andNameLike("%" + querryGoodsList.getName() + "%");
        }
        if (querryGoodsList.getCategoryId() != null && querryGoodsList.getCategoryId() != 0) {
            criteria.andCategoryIdEqualTo(querryGoodsList.getCategoryId());
        }
        if (querryGoodsList.getBrandId() != null) {
            criteria.andBrandIdEqualTo(querryGoodsList.getBrandId());
        }
        if (querryGoodsList.getKeyword() != null) {
            criteria.andKeywordsEqualTo(querryGoodsList.getKeyword());
        }
        if (querryGoodsList.getSort() != null && querryGoodsList.getOrder() != null) {
            goodsExample.setOrderByClause(querryGoodsList.getSort() + " " + querryGoodsList.getOrder());
        }
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

    /**
     * 评价查询
     *
     * @param querryCommentList
     * @return
     */
    @Override
    public List<Comment> commentList(QuerryCommentList querryCommentList) {
        //分页查询
        PageHelper.startPage(querryCommentList.getPage(), querryCommentList.getLimit());
        CommentExample commentExample = new CommentExample();
        CommentExample.Criteria criteria = commentExample.createCriteria();
        criteria.andDeletedNotEqualTo(true);
        //判断是否含有该条件
        if (querryCommentList.getUserId() != null) {
            criteria.andUserIdEqualTo(querryCommentList.getUserId());
        }
        if (querryCommentList.getValueId() != null) {
            criteria.andValueIdEqualTo(querryCommentList.getValueId());
        }
        commentExample.setOrderByClause(querryCommentList.getSort() + " " + querryCommentList.getOrder());
        List<Comment> commentList = commentMapper.selectByExample(commentExample);
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
     * 查询新商品
     *
     * @return
     */
    @Override
    public List<Goods> selectNewGoods() {
        PageHelper.startPage(1,4);
        GoodsExample goodsExample = new GoodsExample();
        goodsExample.createCriteria().andIsNewEqualTo(true);
        return goodsMapper.selectByExampleWithBLOBs(goodsExample);
    }

    /**
     * 查询一级分类
     *
     * @return
     */
    @Override
    public List<Category> selectCategoryL1() {
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.createCriteria().andPidEqualTo(0);
        return categoryMapper.selectByExample(categoryExample);
    }

    /**
     * 查询热门商品
     *
     * @return
     */
    @Override
    public List<Goods> selectHotGoods() {
        PageHelper.startPage(1,4);
        GoodsExample goodsExample = new GoodsExample();
        goodsExample.createCriteria().andIsHotEqualTo(true);
        return goodsMapper.selectByExample(goodsExample);
    }

    @Override
    public List selectCategoryAndGoods() {
        ArrayList<Map> floorGoodsList = new ArrayList<>();
        List<Category> l1Categories = selectCategoryL1();
        for (Category l1Category : l1Categories) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("name", l1Category.getName());
            map.put("id", l1Category.getId());
            CategoryExample categoryExample = new CategoryExample();
            categoryExample.createCriteria().andPidEqualTo(l1Category.getId());
            List<Category> categories = categoryMapper.selectByExample(categoryExample);
            PageHelper.startPage(1,4);
            List<Goods> goodsList = goodsMapper.selectGoodsByCategoryIds(categories);
            map.put("goodsList", goodsList);
            floorGoodsList.add(map);
        }
        return floorGoodsList;
    }

    /**
     * 查询所有的商品数目
     *
     * @return 封装到map中商品数目
     */
    @Override
    public Map goodsCount() {
        long count = goodsMapper.countByExample(null);
        Map<String, Long> map = new HashMap<>();
        map.put("goodsCount", count);
        return map;
    }

    /**
     * 查询商品：
     * currentCategory:Object,
     * brotherCategory:Array,
     * parentCategory:Object
     *
     * @param id 当前商品id
     * @return 查询结果c
     */
    @Override
    public Map selectCategoryByGoodsId(Integer id) {
        //先判断是否是最高级别Category
        Category parentCategory = null;
        Category currentCategory = null;
        List<Category> brotherCategory = null;
        Category category = categoryMapper.selectByPrimaryKey(id);
        if (category.getPid() == 0) {//则该类别是最高级别:parentCategory
            parentCategory = category;
            CategoryExample example = new CategoryExample();
            CategoryExample.Criteria criteria = example.createCriteria();
            criteria.andPidEqualTo(parentCategory.getId());
            brotherCategory = categoryMapper.selectByExample(example);
            currentCategory = brotherCategory.get(0);
        } else {//否则则该类别不是最高级别:currentCategory
            currentCategory = category;
            CategoryExample example = new CategoryExample();
            CategoryExample.Criteria criteria = example.createCriteria();
            criteria.andPidEqualTo(currentCategory.getPid());
            brotherCategory = categoryMapper.selectByExample(example);
            parentCategory = categoryMapper.selectByPrimaryKey(currentCategory.getPid());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("currentCategory", currentCategory);
        map.put("brotherCategory", brotherCategory);
        map.put("parentCategory", parentCategory);
        return map;
    }

    /**
     * 查新商品详情
     * 不要怀疑，这就是一个接口！
     * json:
     * "specificationList":Array[1],
     * "groupon":Array[0],
     * "issue":Array[12],
     * "userHasCollect":1,
     * "comment":Object{...},
     * "attribute":Array[0],
     * "brand":Object{...},
     * "productList":Array[1],
     * "info":Object{...}
     *
     * @param id 商品id
     * @return 商品详情
     */
    @Override
    public GoodsDetail selectGoodsDetailByGoodsId(Integer id) {
        GoodsDetail goodsDetail = new GoodsDetail();
        //查询商品的所有规格:pecificationList
        GoodsSpecificationExample example = new GoodsSpecificationExample();
        example.createCriteria().andGoodsIdEqualTo(id);
        List<GoodsSpecification> goodsSpecifications = goodsSpecificationMapper.selectByExample(example);
        //转化为前台对应形式
        List<SpecificationList> specificationLists = new ArrayList<>();
        //每一个都是对应的数组（前台bug）
        for (GoodsSpecification goodsSpecification : goodsSpecifications) {
            SpecificationList specificationList = new SpecificationList();
            List<GoodsSpecification> list = new ArrayList<>();
            specificationList.setName(goodsSpecification.getSpecification());
            list.add(goodsSpecification);
            specificationList.setValueList(list);
            specificationLists.add(specificationList);
        }
        goodsDetail.setSpecificationList(specificationLists);//ok


        //查看团购：groupon
        //先从groupRule中查询ruleId
        GrouponRulesExample grouponRulesExample = new GrouponRulesExample();
        grouponRulesExample.createCriteria().andGoodsIdEqualTo(id);
        List<GrouponRules> grouponRules = grouponRulesMapper.selectByExample(grouponRulesExample);
        goodsDetail.setGroupon(grouponRules);//ok


        //查询issue
        List<Issue> issues = issueMapper.selectByExample(null);
        goodsDetail.setIssue(issues);//ok

        //查看用户收藏：userHasCollect
        CollectExample collectExample = new CollectExample();
        collectExample.createCriteria().andTypeEqualTo((byte) 0).andValueIdEqualTo(id);
        long count = collectMapper.countByExample(collectExample);
        goodsDetail.setUserHasCollect(count);//ok

        //查看评论：comment
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andValueIdEqualTo(id).andTypeEqualTo((byte) 0);
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        long count1 = commentMapper.countByExample(commentExample);
        Comments comments1 = new Comments();
        List<SingleComment> list = new ArrayList<>();
        for (Comment comment : comments) {
            SingleComment singleComment = new SingleComment();
            singleComment.setId(comment.getId());
            //获得用户昵称
            User user = userMapper.selectByPrimaryKey(comment.getUserId());
            singleComment.setNickname(user.getNickname());
            singleComment.setContent(comment.getContent());
            singleComment.setPicList(comment.getPicUrls());
            singleComment.setAddTime(comment.getAddTime());
            singleComment.setAvatar(user.getAvatar());
            list.add(singleComment);
        }
        comments1.setCount(count1);
        comments1.setData(list);
        goodsDetail.setComment(comments1);//ok

        //查询attribute
        GoodsAttributeExample attributeExample = new GoodsAttributeExample();
        attributeExample.createCriteria().andGoodsIdEqualTo(id);
        List<GoodsAttribute> goodsAttributes = goodsAttributeMapper.selectByExample(attributeExample);
        goodsDetail.setAttribute(goodsAttributes);//ok

        //查询brand
        Goods goods = goodsMapper.selectByPrimaryKey(id);
        Brand brand = brandMapper.selectByPrimaryKey(goods.getBrandId());
        goodsDetail.setBrand(brand);//ok

        //查询productList
        GoodsProductExample goodsProductExample = new GoodsProductExample();
        goodsProductExample.createCriteria().andGoodsIdEqualTo(id);
        List<GoodsProduct> goodsProducts = goodsProductMapper.selectByExample(goodsProductExample);
        goodsDetail.setProductList(goodsProducts);
        //传讯info
        goodsDetail.setInfo(goods);//ok

        return goodsDetail;
    }

    /**
     * 宝
     * 查询所有品牌
     *
     * @param page 页数
     * @param size 每页个数
     * @return 查询数据
     */
    @Override
    public Map selectAllBrand(Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<Brand> brands = brandMapper.selectByExample(null);
        PageInfo<Brand> pageInfo = new PageInfo<>(brands);
        long total = pageInfo.getTotal();
        Map<String, Object> map = new HashMap<>();
        map.put("totalPages", total);
        map.put("brandList", brands);
        return map;
    }

    /**
     * 宝
     * 按照id传讯单个Brand
     *
     * @param id brandID
     * @return 查询结果
     */
    @Override
    public Map selectBrandById(Integer id) {
        Brand brand = brandMapper.selectByPrimaryKey(id);
        Map<String, Object> map = new HashMap<>();
        map.put("brand", brand);
        return map;
    }

    @Override
    public Map selectGoodsByCategoryId(Integer categoryId, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        GoodsExample example = new GoodsExample();
        example.createCriteria().andCategoryIdEqualTo(categoryId);
        List<Goods> goods = goodsMapper.selectByExample(example);
        PageInfo<Goods> pageInfo = new PageInfo<>(goods);
        long total = pageInfo.getTotal();
        List<Category> categories = categoryMapper.selectByExample(null);
        Map<String, Object> map = new HashMap<>();
        map.put("count", total);
        map.put("goodsList", goods);
        map.put("filterCategoryList", categories);
        return map;
    }

    @Override
    public Map selectGoodsRelatedByGoodsId(Integer id) {
        Goods goods = goodsMapper.selectByPrimaryKey(id);
        GoodsExample example = new GoodsExample();
        example.createCriteria().andCategoryIdEqualTo(goods.getCategoryId());
        List<Goods> goods1 = goodsMapper.selectByExample(example);
        Map<String, Object> map = new HashMap<>();
        map.put("goodsList", goods1);
        return map;
    }

    @Override
    public Map selectBrandByBrandId(Integer brandId, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        GoodsExample example = new GoodsExample();
        example.createCriteria().andBrandIdEqualTo(brandId);
        List<Goods> goods = goodsMapper.selectByExample(example);
        PageInfo<Goods> pageInfo = new PageInfo<>(goods);
        long total = pageInfo.getTotal();
        List<Category> categories = categoryMapper.selectByExample(null);
        Map<String,Object> map = new HashMap<>();
        map.put("goodsList",goods);
        map.put("count",total);
        map.put("filterCategoryList",categories);
        return map;
    }

    @Override
    public Map selectGoodsByKeyWord(String keyWord, String sort, String order, Integer categoryId, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        keyWord = "%" + keyWord + "%";
        GoodsExample goodsExample = new GoodsExample();
        goodsExample.setOrderByClause(sort + " " + order);
        GoodsExample.Criteria criteria = goodsExample.createCriteria().andNameLike(keyWord);
        if (categoryId != 0) {
            criteria.andCategoryIdEqualTo(categoryId);
        }
        List<Goods> goods = goodsMapper.selectByExample(goodsExample);
        PageInfo<Goods> pageInfo = new PageInfo<>(goods);
        long total = pageInfo.getTotal();
        List<Category> categories = categoryMapper.selectByExample(null);
        Map<String, Object> map = new HashMap<>();
        map.put("goodsList", goods);
        map.put("count", total);
        map.put("filterCategoryList", categories);
        return map;
    }

    @Override
    public Map selectAllNewGoods(Boolean isNew,Integer page,Integer size,String order,String sort,Integer categoryId) {
        PageHelper.startPage(page,size);
        GoodsExample goodsExample = new GoodsExample();
        goodsExample.setOrderByClause(sort + " "+ order);
        goodsExample.createCriteria().andIsNewEqualTo(isNew);
        List<Goods> goods = goodsMapper.selectByExample(goodsExample);
        PageInfo<Goods> pageInfo = new PageInfo<>(goods);
        long total = pageInfo.getTotal();
        List<Category> categories = categoryMapper.selectByExample(null);
        Map<String,Object> map = new HashMap<>();
        map.put("goodsList",goods);
        map.put("count",total);
        map.put("filterCategoryList",categories);
        return map;
    }

    @Override
    public Map selectAllHotGoods(Boolean isHot, Integer page, Integer size, String order, String sort, Integer categoryId) {
        PageHelper.startPage(page,size);
        GoodsExample goodsExample = new GoodsExample();
        goodsExample.setOrderByClause(sort + " "+ order);
        goodsExample.createCriteria().andIsHotEqualTo(isHot);
        List<Goods> goods = goodsMapper.selectByExample(goodsExample);
        PageInfo<Goods> pageInfo = new PageInfo<>(goods);
        long total = pageInfo.getTotal();
        List<Category> categories = categoryMapper.selectByExample(null);
        Map<String,Object> map = new HashMap<>();
        map.put("goodsList",goods);
        map.put("count",total);
        map.put("filterCategoryList",categories);
        return map;
    }
}
