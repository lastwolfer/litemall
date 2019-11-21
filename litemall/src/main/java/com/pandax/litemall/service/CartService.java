package com.pandax.litemall.service;

import com.pandax.litemall.bean.Cart;
import com.pandax.litemall.bean.CartCondition;

import java.util.Map;

public interface CartService {
    Map<String, Object> cartIndex();

    int cartAdd(Cart cart);

    int cartFastadd(Cart cart);

    void cartUpdate(Cart cart);

    Map<String, Object> cartCheckde(CartCondition cartCondition);

    Map<String, Object> cartDelete(CartCondition cartCondition);

    Map<String, Object> collectList(Integer type, Integer page, Integer size);
}
