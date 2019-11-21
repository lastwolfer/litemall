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
    //            "content": "<img src=\"//yanxuan.nosdn.127.net/75c55a13fde5eb2bc2dd6813b4c565cc.jpg\">\n    <img src=\"//yanxuan.nosdn.127.net/e27e1de2b271a28a21c10213b9df7e95.jpg\">\n    <img src=\"//yanxuan.nosdn.127.net/9d413d1d28f753cb19096b533d53418d.jpg\">\n    <img src=\"//yanxuan.nosdn.127.net/64b0f2f350969e9818a3b6c43c217325.jpg\">\n    <img src=\"//yanxuan.nosdn.127.net/a668e6ae7f1fa45565c1eac221787570.jpg\">\n    <img src=\"//yanxuan.nosdn.127.net/0d4004e19728f2707f08f4be79bbc774.jpg\">\n    <img src=\"//yanxuan.nosdn.127.net/79ee021bbe97de7ecda691de6787241f.jpg\">"
    //        }
    //    ],
    //    "errmsg": "成功"
    //}
    @RequestMapping("wx/topic/related")
    public BaseReqVo related(int id) {
        List<Topic> topics = topicService.getTopicRelated(id);
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        baseReqVo.setErrno(0);
        baseReqVo.setData(topics);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }

//Request URL: http://192.168.2.100:8081/wx/topic/list?page=1&size=10
    @RequestMapping("wx/topic/list")
    public BaseReqVo related(int page,int size) {
        List<Topic> topics = topicService.getTopic(page,size);
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        Map<String, Object> map = new HashMap<>();
        map.put("data",topics);
        map.put("count",topics.size());
        baseReqVo.setErrno(0);
        baseReqVo.setData(map);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }
    //{
    //    "errno": 0,
    //    "data": {
    //        "data": [
    //            {
    //                "id": 358,
    //                "title": "车",
    //                "subtitle": "汽车",
    //                "price": 10,
    //                "readCount": "10k",
    //                "picUrl": "http://192.168.2.100:8081/wx/storage/fetch/kwig1o5f6sf6e5md3b8r.jpg"
    //            },
    //            {
    //                "id": 350,
    //                "title": "酒桶,尼古拉斯---小肥羊",
    //                "subtitle": "皮肤加成--逃逸速度5分半",
    //                "price": 2312312,
    //                "readCount": "546567567",
    //                "picUrl": "http://192.168.2.100:8081/wx/storage/fetch/rrpsbyhm976pq7yzqsvz.jpg"
    //            }
    //        ],
    //        "count": 41
    //    },
    //    "errmsg": "成功"
    //}
}
