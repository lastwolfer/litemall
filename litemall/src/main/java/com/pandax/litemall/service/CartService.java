package com.pandax.litemall.service;

import com.pandax.litemall.bean.*;

import java.util.List;
import java.util.Map;

public interface CartService {
    Map<String, Object> cartIndex(User user);

    int cartAdd(Cart cart, User user);

    int cartFastadd(Cart cart, User user);

    void cartUpdate(Cart cart, User user);

    Map<String, Object> cartCheckde(CartCondition cartCondition, User user);

    Map<String, Object> cartDelete(CartCondition cartCondition, User user);

    Map<String, Object> collectList(Integer type, Integer page, Integer size, User user);

    Map<String, Object> collectAddOrDelete(Map<String, Integer> map, User user);

    CartCheckedCondition cartCheckout(Integer cartId, Integer addressId, Integer cartId1, Integer grouponRulesId, User user);

    Integer cartGoodscount(User user);
}
