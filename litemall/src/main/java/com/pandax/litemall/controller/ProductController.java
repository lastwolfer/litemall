package com.pandax.litemall.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.pandax.litemall.bean.*;
import com.pandax.litemall.mapper.CouponMapper;
import com.pandax.litemall.mapper.GrouponMapper;
import com.pandax.litemall.service.GoodsService;
import com.pandax.litemall.service.GrouponService;
import com.pandax.litemall.service.SearchService;
import com.pandax.litemall.service.UserService;
import com.pandax.reponseJson.GoodsDetail;
import com.pandax.reponseJson.GrouponGoods;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ProductController {


    @Autowired
    GoodsService goodsService;

    @Autowired
    SearchService searchService;

    @Autowired
    UserService userService;

    @Autowired
    GrouponService grouponService;


    /**
     * 商品列表,分页和排序
     */
    @RequestMapping("admin/goods/list")
    public BaseReqVo goodsList(QuerryGoodsList querryGoodsList) {
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        List<Goods> goodsList = goodsService.goodsList(querryGoodsList);
        long total = new PageInfo<>(goodsList).getTotal();
        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("items", goodsList);

        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setData(map);
        return baseReqVo;
    }

    /**
     * 商品上架信息填写
     *
     * @return
     */
    @RequestMapping("admin/goods/catAndBrand")
    public BaseReqVo catAndBrand() {
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        List<CategoryList> categoryList = goodsService.QuerryCat();
        List brandList = goodsService.QuerryBrand();
        Map<String, Object> map = new HashMap<>();
        map.put("categoryList", categoryList);
        map.put("brandList", brandList);
        baseReqVo.setErrno(0);
        baseReqVo.setData(map);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }

    /**
     * 商品上架
     *
     * @param
     * @return
     */
    @RequestMapping("admin/goods/create")
    public BaseReqVo createGoods(@RequestBody GoodsCreateBean goodsCreateBean) throws JsonProcessingException {
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        int i = goodsService.createGoods(goodsCreateBean);
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }

    /**
     * 根据id 查询商品信息
     *
     * @param id
     * @return
     */
    @RequestMapping("admin/goods/detail")
    public BaseReqVo selectGoodsById(Integer id) {
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        GoodsCreateBean goodsCreateBean = goodsService.selectGoodsById(id);
        baseReqVo.setErrno(0);
        baseReqVo.setData(goodsCreateBean);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }

    /**
     * 商品信息更新
     *
     * @param goodsCreateBean
     * @return
     */
    @RequestMapping("admin/goods/update")
    public BaseReqVo updateGoods(@RequestBody GoodsCreateBean goodsCreateBean) {
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        int i = goodsService.updateGoods(goodsCreateBean);
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }

    /**
     * 删除商品
     *
     * @param map
     * @return
     */
    @RequestMapping("admin/goods/delete")
    public BaseReqVo deleteGoods(@RequestBody Map map) {
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        int i = goodsService.deleteGoods(map);
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }

    /**
     * 所以评论
     * @param querryCommentList
     * @return
     */
    @RequestMapping("admin/comment/list")
    public BaseReqVo commentList(QuerryCommentList querryCommentList){
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        List<Comment> commentList = goodsService.commentList(querryCommentList);
        long total = new PageInfo<>(commentList).getTotal();
        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("items", commentList);
        baseReqVo.setErrno(0);
        baseReqVo.setData(map);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }

    /**
     * 评论回复
     * @param commentReply
     * @return
     */
    @RequestMapping("admin/order/reply")
    public BaseReqVo reply(@RequestBody CommentReply commentReply){
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        int i = goodsService.reply(commentReply);
        if (i == 0){
            baseReqVo.setErrno(622);
            baseReqVo.setErrmsg("订单商品已回复！");
        }else {
            baseReqVo .setErrno(0);
            baseReqVo.setErrmsg("成功");
        }
        return baseReqVo;
    }

    /**
     * 删除评论
     * @param comment
     * @return
     */
    @RequestMapping("/admin/comment/delete")
    public BaseReqVo deleteComment(@RequestBody Comment comment){
        BaseReqVo<Override> baseReqVo = new BaseReqVo<>();
        int i = goodsService.deleteComment(comment);
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }


    /**
     *查询所有的商品数目
     * @return json的数据
     */
    @RequestMapping("/wx/goods/count")
    public BaseReqVo wxGoodsCount(){
        BaseReqVo baseReqVo = new BaseReqVo();
        Map map = goodsService.goodsCount();
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setData(map);
        return baseReqVo;
    }


    /**
     *查询商品：
     * currentCategory:Object,
     *  brotherCategory:Array,
     *  parentCategory:Object
     * @param id 当前商品的ID
     * @return json数据
     */
    @RequestMapping("/wx/goods/category")
    public BaseReqVo wxGoodsCategory(Integer id){
        BaseReqVo baseReqVo = new BaseReqVo();
        Map map = goodsService.selectCategoryByGoodsId(id);
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setData(map);
        System.out.println(baseReqVo);
        return baseReqVo;
    }


    /**
     * 宝
     * 查询所属类别的商品
     * @param categoryId 类别id
     * @param page 页数
     * @param size 每页个数
     * @return json
     */
    //keyword=123&page=1&size=20&sort=name&order=desc&categoryId=0
    @RequestMapping("/wx/goods/list")
    public BaseReqVo wxGoodsList(Integer categoryId,Integer brandId,Integer page,Integer size,
                                 String keyword,String sort,String order){
        BaseReqVo baseReqVo = new BaseReqVo();
        Map map =null;
        if(keyword != null ){
            Subject subject = SecurityUtils.getSubject();
            User user = (User) subject.getPrincipal();
            if (user != null){
                searchService.saveOrUpdateHistory(user.getId(),keyword);
            }
            map = goodsService.selectGoodsByKeyWord(keyword,sort,order,categoryId,page,size);
            baseReqVo.setErrmsg("成功");
            baseReqVo.setData(map);
            return baseReqVo;
        }
        if(categoryId != null) {
            map = goodsService.selectGoodsByCategoryId(categoryId, page, size);
        }
        if(brandId != null){
            map = goodsService.selectBrandByBrandId(brandId,page, size);
        }
        baseReqVo.setErrmsg("成功");
        baseReqVo.setData(map);
        return baseReqVo;
    }



    @RequestMapping("/wx/goods/detail")
    public BaseReqVo wxGoodsDetail(Integer id){
        BaseReqVo baseReqVo = new BaseReqVo();
        GoodsDetail goodsDetail = goodsService.selectGoodsDetailByGoodsId(id);
        //添加足迹
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if (user!=null){
            Integer i = userService.insertFootprint(id,user.getId());
        }
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setData(goodsDetail);
        System.out.println(baseReqVo);
        return baseReqVo;
    }



    /**
     * 宝
     * 查询所有品牌
     * @param page 页数
     * @param size 每页个数
     * @return json数据
     */
    @RequestMapping("/wx/brand/list")
    public BaseReqVo wxBrandList(Integer page,Integer size){
        BaseReqVo baseReqVo = new BaseReqVo();
        Map map = goodsService.selectAllBrand(page, size);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setData(map);
        return baseReqVo;
    }

    /**
     * 宝
     * 按照id传讯单个Brand
     * @param id brandID
     * @return 查询结果
     */
    @RequestMapping("/wx/brand/detail")
    public BaseReqVo wxBrandDetail(Integer id){
        BaseReqVo baseReqVo = new BaseReqVo();
        Map map = goodsService.selectBrandById(id);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setData(map);
        return baseReqVo;
    }

    /**
     * 查询关联商品
     * @param id 商品id
     * @return json数据
     */
    @RequestMapping("/wx/goods/related")
    public BaseReqVo wxGoodsRelated(Integer id){
        BaseReqVo baseReqVo = new BaseReqVo();
        Map map = goodsService.selectGoodsRelatedByGoodsId(id);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setData(map);
        return baseReqVo;
    }


    @RequestMapping("/wx/groupon/list")
    public BaseReqVo wxGroupList(Integer page,Integer size){
        BaseReqVo baseReqVo = new BaseReqVo();
        List<GrouponGoods> grouponGoods = grouponService.selectAllGrouponList(page,size);
        Map<String,Object> map = new HashMap<>();
        map.put("data",grouponGoods);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setData(map);
        return baseReqVo;
    }




}
