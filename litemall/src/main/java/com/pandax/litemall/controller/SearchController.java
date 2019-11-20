package com.pandax.litemall.controller;

import com.github.pagehelper.PageInfo;
import com.pandax.litemall.bean.Goods;
import com.pandax.litemall.bean.QuerryGoodsList;
import com.pandax.litemall.service.GoodsService;
import com.pandax.litemall.utils.BaseRespVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class SearchController {
    //  SearchIndex: WxApiRoot + 'search/index', //搜索关键字
    //  SearchResult: WxApiRoot + 'search/result', //搜索结果
    //  SearchHelper: WxApiRoot + 'search/helper', //搜索帮助
    //  SearchClearHistory: WxApiRoot + 'search/clearhistory', //搜索历史清楚


    @Autowired
    GoodsService goodsService;

    @RequestMapping("wx/search/helper")
    public BaseRespVo serchHelper(String keyword){
        //{"errno":0,"data":["520元礼包抢先领"],"errmsg":"成功"}
        BaseRespVo baseRespVo = new BaseRespVo();
        String[] data = {keyword};
        baseRespVo.setErrno(0);
        baseRespVo.setData(data);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }

    @RequestMapping("wx/search/clearhistory")
    public BaseRespVo clearHistory(QuerryGoodsList querryGoodsList){
        BaseRespVo baseRespVo = new BaseRespVo();
        List<Goods> goodsList = goodsService.goodsList(querryGoodsList);
        long total = new PageInfo<>(goodsList).getTotal();
        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("items", goodsList);

        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        baseRespVo.setData(map);
        return baseRespVo;
    }

//http://192.168.2.100:8081/wx/search/index
    //{
    //    "errno": 0,
    //    "data": {
    //        "defaultKeyword": {
    //            "id": 6,
    //            "keyword": "520元礼包抢先领",
    //            "url": "",
    //            "isHot": true,
    //            "isDefault": true,
    //            "sortOrder": 1,
    //            "addTime": "2018-01-31 19:00:00",
    //            "updateTime": "2018-01-31 19:00:00",
    //            "deleted": false
    //        },
    //        "hotKeywordList": [
    //            {
    //                "id": 6,
    //                "keyword": "520元礼包抢先领",
    //                "url": "",
    //                "isHot": true,
    //                "isDefault": true,
    //                "sortOrder": 1,
    //                "addTime": "2018-01-31 19:00:00",
    //                "updateTime": "2018-01-31 19:00:00",
    //                "deleted": false
    //            }
    //        ],
    //        "historyKeywordList": [{"keyword": "123"}]
    //    },
    //    "errmsg": "成功"
    //}
    @RequestMapping("wx/search/index")
    public BaseRespVo index() {


    }
}
