package com.pandax.litemall.controller;

import com.pandax.litemall.bean.BaseReqVo;
import com.pandax.litemall.bean.Cart;
import com.pandax.litemall.bean.CartCondition;
import com.pandax.litemall.service.CartService;
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
        Map<String,Object> map=cartService.cartIndex();
        return BaseReqVo.ok(map);
    }
    @RequestMapping("wx/cart/add")
    public BaseReqVo cartAdd(@RequestBody Cart cart){
        int number=cartService.cartAdd(cart);
        return BaseReqVo.ok(number);
    }
    @RequestMapping("wx/cart/fastadd")
    public BaseReqVo cartFastadd(@RequestBody Cart cart){
        int number=cartService.cartFastadd(cart);
        return BaseReqVo.ok(number);
    }
    @RequestMapping("wx/cart/update")
    public BaseReqVo cartUpdate(@RequestBody Cart cart){
        cartService.cartUpdate(cart);
        return BaseReqVo.ok();
    }
    @RequestMapping("wx/cart/delete")
    public BaseReqVo cartDelete(@RequestBody CartCondition cartCondition){
        Map<String,Object> map=cartService.cartDelete(cartCondition);
        return BaseReqVo.ok(map);
    }
    @RequestMapping("wx/cart/checked")
    public BaseReqVo cartCheckde(@RequestBody CartCondition cartCondition){
        System.out.println(cartCondition);
        Map<String,Object> map=cartService.cartCheckde(cartCondition);
        return BaseReqVo.ok(map);
    }

    @RequestMapping("wx/collect/list")
    public BaseReqVo collectList(Integer type,Integer page,Integer size){
        Map<String,Object> map=cartService.collectList(type,page,size);
        return BaseReqVo.ok(map);
    }
}
