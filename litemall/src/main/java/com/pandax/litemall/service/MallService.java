package com.pandax.litemall.service;

import com.pandax.litemall.bean.*;

import java.util.HashMap;

public interface MallService {
    public Region[] region();


    HashMap<String, Object> brand(Integer page, Integer limit, String sort,  String order,Integer id,String name);

    Brand brandUpdate(Brand newBrand);

    void brandDelete(Brand newBrand);

    Brand brandCreate(Brand newBrand);

    Category[] categoryList();

    Category[] categoryL1();

    Category categoryCreate(Category category);

    void categoryUpdate(Category category);

    void categoryDelete(Category category);

    HashMap<String, Object> orderList(Integer page, Integer limit, Integer orderStatusArray, String sort, String order, Integer userId, String orderSn);

    HashMap<String, Object> orderDetail(Integer id);

    HashMap<String, Object> issueList(Integer page, Integer limit, String question, String sort, String order);

    HashMap<String, Object> keywordList(Integer page, Integer limit, String keyword, String url, String sort, String order);

    Keyword keywordUpdate(Keyword keyword);

    void keywordDelete(Keyword keyword);

    Keyword keywordCreate(Keyword keyword);

    Issue issueUpdate(Issue issue);

    void issueDelete(Issue issue);
}
