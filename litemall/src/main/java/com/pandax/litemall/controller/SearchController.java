package com.pandax.litemall.controller;


import com.pandax.litemall.bean.*;
import com.pandax.litemall.service.GoodsService;
import com.pandax.litemall.service.SearchService;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class SearchController {

    @Autowired
    GoodsService goodsService;

    @Autowired
    SearchService searchService;

    @RequestMapping("wx/search/helper")
    public BaseReqVo serchHelper(String keyword){
        BaseReqVo baseReqVo = new BaseReqVo();
        String[] helper = searchService.getHelper(keyword);
        baseReqVo.setErrno(0);
        baseReqVo.setData(helper);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }


    @RequestMapping("wx/search/index")
    public BaseReqVo index() {
        BaseReqVo baseReqVo = new BaseReqVo();
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        String[] historyList = null;
        if (user != null){
            historyList = searchService.getHistoryList(user.getId());
            List<Map> historyKeywordList = new ArrayList<>();
            for (String s : historyList) {
                HashMap<String, Object> stringObjectHashMap = new HashMap<>();
                stringObjectHashMap.put("keyword",s);
                historyKeywordList.add(stringObjectHashMap);
            }
            Keyword defaultKeyword = searchService.getDefault();
            List<Keyword> hots = searchService.getHot();
            Map<String, Object> map = new HashMap<>();
            map.put("defaultKeyword", defaultKeyword);
            map.put("hotKeywordList", hots);
            map.put("historyKeywordList", historyKeywordList);
            baseReqVo.setErrno(0);
            baseReqVo.setErrmsg("成功");
            baseReqVo.setData(map);
            return baseReqVo;
        }
        Keyword defaultKeyword = searchService.getDefault();
        List<Keyword> hots = searchService.getHot();
        Map<String, Object> map = new HashMap<>();
        map.put("defaultKeyword", defaultKeyword);
        map.put("hotKeywordList", hots);
        map.put("historyKeywordList", null);
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setData(map);
        return baseReqVo;
    }

    @RequestMapping("wx/search/clearhistory")
    public BaseReqVo clearHistrory() {
        BaseReqVo baseReqVo = new BaseReqVo();
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        Integer id = user.getId();
        int i = searchService.clearHistrory(id);
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }
}
