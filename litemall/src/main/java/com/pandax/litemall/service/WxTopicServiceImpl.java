package com.pandax.litemall.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pandax.litemall.bean.Topic;
import com.pandax.litemall.mapper.TopicMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class WxTopicServiceImpl implements WxTopicService {

    @Autowired
    TopicMapper topicMapper;

    @Override
    public Topic getTopicDetail(int id) {
        return topicMapper.getTopicDetail(id);
    }

    @Override
    public List<Topic> getTopicRelated(int id) {
        List<Topic> topicRelated = topicMapper.getTopicRelated(id);
        return topicRelated;
    }

    @Override
    public List<Topic> getTopic(int page,int size) {
        PageHelper.startPage(page,size);
        List<Topic> topic = topicMapper.getTopic();
        PageInfo<Topic> topicPageInfo = new PageInfo<>(topic);
        return topic;
    }
}
