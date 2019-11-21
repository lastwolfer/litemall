package com.pandax.litemall.controller;

import com.pandax.litemall.bean.*;
import com.pandax.litemall.service.CartService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class CartController {

    @Autowired
    CartService cartService;
    @RequestMapping("wx/cart/index")
    public BaseReqVo cartIndex(){
        Subject subject = SecurityUtils.getSubject();
        User user = (User)subject.getPrincipal();
        Map<String,Object> map=cartService.cartIndex(user);
        return BaseReqVo.ok(map);
    }
    @RequestMapping("wx/cart/add")
    public BaseReqVo cartAdd(@RequestBody Cart cart){
        Subject subject = SecurityUtils.getSubject();
        User user = (User)subject.getPrincipal();
        int number=cartService.cartAdd(cart,user);
        return BaseReqVo.ok(number);
    }
    @RequestMapping("wx/cart/fastadd")
    public BaseReqVo cartFastadd(@RequestBody Cart cart){
        Subject subject = SecurityUtils.getSubject();
        User user = (User)subject.getPrincipal();
        int number=cartService.cartFastadd(cart,user);
        return BaseReqVo.ok(number);
    }
    @RequestMapping("wx/cart/update")
    public BaseReqVo cartUpdate(@RequestBody Cart cart){
        Subject subject = SecurityUtils.getSubject();
        User user = (User)subject.getPrincipal();
        cartService.cartUpdate(cart,user);
        return BaseReqVo.ok();
    }
    @RequestMapping("wx/cart/delete")
    public BaseReqVo cartDelete(@RequestBody CartCondition cartCondition){
        Subject subject = SecurityUtils.getSubject();
        User user = (User)subject.getPrincipal();
        Map<String,Object> map=cartService.cartDelete(cartCondition,user);
        return BaseReqVo.ok(map);
    }
    @RequestMapping("wx/cart/checked")
    public BaseReqVo cartCheckde(@RequestBody CartCondition cartCondition){
        Subject subject = SecurityUtils.getSubject();
        User user = (User)subject.getPrincipal();
        System.out.println(cartCondition);
        Map<String,Object> map=cartService.cartCheckde(cartCondition,user);
        return BaseReqVo.ok(map);
    }

    @RequestMapping("wx/collect/list")
    public BaseReqVo collectList(Integer type,Integer page,Integer size){
        Subject subject = SecurityUtils.getSubject();
        User user = (User)subject.getPrincipal();
        Map<String,Object> map=cartService.collectList(type,page,size,user);
        return BaseReqVo.ok(map);
    }
    @RequestMapping("wx/collect/addordelete")
    public BaseReqVo collectAddOrDelete(@RequestBody Map<String,Integer> map){
        Subject subject = SecurityUtils.getSubject();
        User user = (User)subject.getPrincipal();
        Map<String,Object> map1=cartService.collectAddOrDelete(map,user);
        return BaseReqVo.ok(map1);
    }
    @RequestMapping("wx/cart/checkout")
    public BaseReqVo cartCheckout(Integer cartId,Integer addressId,Integer couponId,Integer grouponRulesId){
        Subject subject = SecurityUtils.getSubject();
        User user = (User)subject.getPrincipal();
        CartCheckedCondition map=cartService.cartCheckout(cartId,addressId,couponId,grouponRulesId,user);
        return BaseReqVo.ok(map);
    }

    @RequestMapping("wx/cart/goodscount")
    public BaseReqVo cartGoodscount(){
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        Integer number=cartService.cartGoodscount(user);
        return BaseReqVo.ok(number);
    }
}
