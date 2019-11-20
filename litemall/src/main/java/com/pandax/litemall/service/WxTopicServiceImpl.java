package com.pandax.litemall.service;

import com.pandax.litemall.bean.Topic;
import com.pandax.litemall.mapper.TopicMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return topicMapper.getTopicRelated(id);
    }
}
