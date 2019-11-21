package com.pandax.litemall.service;

import com.pandax.litemall.bean.Topic;

import java.util.List;

public interface WxTopicService {
    Topic getTopicDetail(int id);

    List<Topic> getTopicRelated(int id);

    List<Topic> getTopic(int page,int size);
}
