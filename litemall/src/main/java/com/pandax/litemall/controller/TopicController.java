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

    @RequestMapping("wx/topic/related")
    public BaseReqVo related(int id) {
        List<Topic> topics = topicService.getTopicRelated(id);
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        baseReqVo.setErrno(0);
        baseReqVo.setData(topics);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }


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

}
