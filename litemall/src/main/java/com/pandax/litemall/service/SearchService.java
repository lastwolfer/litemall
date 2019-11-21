package com.pandax.litemall.service;

import com.pandax.litemall.bean.Keyword;

import java.util.List;

public interface SearchService {
    int clearHistrory(int id);

    String[] getHistoryList(int id);

    Keyword getDefault();

    List<Keyword> getHot();

    String[] getHelper(String keyword);

    int saveOrUpdateHistory(int id,String keyword);
}
