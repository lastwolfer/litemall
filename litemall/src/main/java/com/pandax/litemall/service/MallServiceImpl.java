package com.pandax.litemall.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pandax.litemall.bean.*;
import com.pandax.litemall.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class MallServiceImpl implements MallService {
    @Autowired
    RegionMapper regionMapper;
    @Autowired
    BrandMapper brandMapper;

    @Override
    public Region[] region() {
        Region[] regions = regionMapper.setlectAllRegion();
        for (Region region : regions) {
            for (Region child : region.getChildren()) {
                Region[] regions1 = regionMapper.selectByPid(child.getId());
                child.setChildren(regions1);
                System.out.println(Arrays.toString(regions1));
            }
        }
        return regions;
    }

    @Override
    public HashMap<String, Object> brand(Integer page, Integer limit, String sort, String order, Integer id, String name) {
        PageHelper.startPage(page, limit);
        HashMap<String, Object> map = new HashMap<>();

        List<Brand> brands = null;
        brands = brandMapper.selectAllBrands(sort, order,id,name);
        PageInfo<Brand> brandPageInfo = new PageInfo<>(brands);
        long total = brandPageInfo.getTotal();
        map.put("total", total);
        map.put("items", brands);
        return map;
    }

    @Override
    public Brand brandUpdate(Brand newBrand) {
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        /*String time = format.format(new Date());*/
        newBrand.setUpdateTime(new Date());
        brandMapper.updateByExample(newBrand);
        Brand brand = brandMapper.selectByPrimaryKey(newBrand.getId());
        return brand;
    }

    @Override
    public void brandDelete(Brand newBrand) {
        brandMapper.deleteByPrimaryKey(newBrand.getId());
    }

    @Override
    public Brand brandCreate(Brand newBrand) {
        newBrand.setAddTime(new Date());
        newBrand.setUpdateTime(new Date());
        brandMapper.insert(newBrand);
        Brand brand = brandMapper.selectByName(newBrand.getName());
        return brand;
    }

    @Autowired
    CategoryMapper categoryMapper;
    @Override
    public Category[] categoryList() {
        Category[] categories=categoryMapper.selectCategoryList();
        System.out.println(Arrays.toString(categories));
        return categories;
    }

    @Override
    public Category[] categoryL1() {
        return categoryMapper.categoryL1();
    }

    @Override
    public Category categoryCreate(Category category) {
        category.setAddTime(new Date());
        category.setUpdateTime(new Date());
        Integer insert = categoryMapper.insert(category);

        Category category1 = categoryMapper.selectByPrimaryKey(category.getId());
        System.out.println(category);
        return category;
    }

    @Override
    public void categoryUpdate(Category category) {
        categoryMapper.updateByPrimaryKey(category);
    }

    @Override
    public void categoryDelete(Category category) {
        categoryMapper.deleteByPrimaryKey(category.getId());
        //删除与之相关的内容  pid=id
        categoryMapper.deleteByPid(category.getId());
    }

    @Autowired
    OrderMapper orderMapper;
    @Override
    public HashMap<String, Object> orderList(Integer page, Integer limit, Integer orderStatusArray, String sort, String order, Integer userId, String orderSn) {
        PageHelper.startPage(page,limit);

        List<Order> orders=orderMapper.selectByCondition(orderStatusArray,sort,order,userId,orderSn);
        PageInfo<Order> orderPageInfo = new PageInfo<>(orders);

        long total = orderPageInfo.getTotal();
        HashMap<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("items",orders);
        return map;
    }


    @Autowired
    OrderGoodsMapper orderGoodsMapper;
    @Override
    public HashMap<String, Object> orderDetail(Integer id) {
        HashMap<String, Object> map = new HashMap<>();
        //首先通过id获取订单详情
        Order order = orderMapper.selectByPrimaryKey(id);
        //将订单信息存入map
        map.put("order",order);
        //通过订单获取用户的id和商品详情的goodsid=id
        Integer userId = order.getUserId();
        //获取用户信息
        User user = new User();
        user.setNickname("haha");
        user.setAvatar("");
        map.put("user",user);
        //获取商品信息
        OrderGoods[] orderGoods = orderGoodsMapper.selectByGoodsId(id);
        map.put("orderGoods",orderGoods);
        return map;
    }

    @Autowired
    IssueMapper issueMapper;
    @Override
    public HashMap<String, Object> issueList(Integer page, Integer limit, String question, String sort, String order) {


        if(question!=null&&!"".equals(question)){
            question="%"+question+"%";
        }

        HashMap<String, Object> map = new HashMap<>();
        PageHelper.startPage(page,limit);
        List<Issue> issues=issueMapper.selectByCondition(question,sort,order);
        PageInfo<Issue> issuePageInfo = new PageInfo<>(issues);
        long total = issuePageInfo.getTotal();
        map.put("total",total);
        map.put("items",issues);
        return map;
    }

    @Autowired
    KeywordMapper keywordMapper;
    @Override
    public HashMap<String, Object> keywordList(Integer page, Integer limit, String keyword, String url, String sort, String order) {
        if(keyword!=null&&!"".equals(keyword)){
            keyword="%"+keyword+"%";
        }
        if(url!=null&&!"".equals(url)){
            url="%"+url+"%";
        }

        PageHelper.startPage(page,limit);
        HashMap<String, Object> map = new HashMap<>();

        List<Keyword> list=keywordMapper.selectByCondition(keyword,url,sort,order);
        PageInfo<Keyword> issuePageInfo = new PageInfo<>(list);
        long total = issuePageInfo.getTotal();

        map.put("items",list);
        map.put("total",total);
        return map;
    }

    @Override
    public Keyword keywordUpdate(Keyword keyword) {
        keyword.setUpdateTime(new Date());
        keywordMapper.updateByPrimaryKey(keyword);
        Keyword keyword1 = keywordMapper.selectByPrimaryKey(keyword.getId());
        return keyword1;
    }

    @Override
    public void keywordDelete(Keyword keyword) {
        keywordMapper.deleteByPrimaryKey(keyword.getId());
    }

    @Override
    public Keyword keywordCreate(Keyword keyword) {
        keyword.setAddTime(new Date());
        keyword.setUpdateTime(new Date());
        keyword.setSortOrder(1);
        keywordMapper.insert(keyword);
        Keyword keyword1 = keywordMapper.selectByPrimaryKey(keyword.getId());
        return keyword1;
    }

}
