package com.pandax.litemall.service;

import com.pandax.litemall.bean.Keyword;
import com.pandax.litemall.mapper.GoodsMapper;
import com.pandax.litemall.mapper.HistoryMapper;
import com.pandax.litemall.mapper.KeywordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    HistoryMapper historyMapper;

    @Autowired
    KeywordMapper keywordMapper;

    @Override
    public int clearHistrory(int id) {
        return historyMapper.deleleById(id);
    }

    @Override
    public String[] getHistoryList(int id) {
        return historyMapper.getHistoryList(id);
    }

    @Override
    public Keyword getDefault() {
        return keywordMapper.selectDefault();
    }

    @Override
    public List<Keyword> getHot() {
        return keywordMapper.selectHot();
    }

    @Override
    public String[] getHelper(String keyword) {
        return keywordMapper.getHelper(keyword);
    }
}
