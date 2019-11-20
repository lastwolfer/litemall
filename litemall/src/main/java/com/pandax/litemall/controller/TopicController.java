package com.pandax.litemall.controller;

import com.pandax.litemall.bean.BaseReqVo;
import com.pandax.litemall.bean.Topic;
import com.pandax.litemall.service.WxTopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TopicController {



    @Autowired
    WxTopicService topicService;

    //http://192.168.2.100:8081/wx/topic/detail?id=358
    //{
    //    "errno": 0,
    //    "data": {
    //        "topic": {
    //            "id": 358,
    //            "title": "车",
    //            "subtitle": "汽车",
    //            "price": 10,
    //            "readCount": "10k",
    //            "picUrl": "http://192.168.2.100:8081/wx/storage/fetch/kwig1o5f6sf6e5md3b8r.jpg",
    //            "sortOrder": 100,
    //            "goods": [],
    //            "addTime": "2019-10-03 06:49:09",
    //            "updateTime": "2019-10-03 06:49:09",
    //            "deleted": false,
    //            "content": "<p>1<\/p>"
    //        },
    //        "goods": []
    //    },
    //    "errmsg": "成功"
    //}
    @RequestMapping("wx/topic/detail")
    public BaseReqVo detail(int id) {
        Topic topic = topicService.getTopicDetail(id);
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        Map<String, Object> map = new HashMap<>();
        map.put("topic", topic);
        map.put("goods", new String[0]);
        baseReqVo.setErrno(0);
        baseReqVo.setData(map);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }

    //{
    //    "errno": 0,
    //    "data": [
    //        {
    //            "id": 264,
    //            "title": "设计师们推荐的应季好物",
    //            "subtitle": "原创",
    //            "price": 29.9,
    //            "readCount": "77.7k",
    //            "picUrl": "https://yanxuan.nosdn.127.net/14918201901050274.jpg",
    //            "sortOrder": 0,
    //            "goods": [],
    //            "addTime": "2018-01-31 19:00:00",
    //            "updateTime": "2019-08-23 04:23:12",
    //            "deleted": false,
    //            "content": ""
    //        },
    //        {
    //            "id": 266,
    //            "title": "一条丝巾就能提升时髦度",
    //            "subtitle": "不知道大家对去年G20时，严选与国礼制造商一起推出的《凤凰于飞》等几款丝巾是否还...",
    //            "price": 100,
    //            "readCount": "35.0k",
    //            "picUrl": "https://yanxuan.nosdn.127.net/14919007135160213.jpg",
    //            "sortOrder": 0,
    //            "goods": [],
    //            "addTime": "2018-01-31 19:00:00",
    //            "updateTime": "2019-10-05 07:56:19",
    //            "deleted": false,
    //            "content": "
    //        }
    //    ],
    //    "errmsg": "成功"
    //}
    @RequestMapping("wx/topic/related")
    public BaseReqVo related(int id) {
        List<Topic> topics = topicService.getTopicRelated(id);
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        Map<String, Object> map = new HashMap<>();

        baseReqVo.setErrno(0);
        baseReqVo.setData(map);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }
}
