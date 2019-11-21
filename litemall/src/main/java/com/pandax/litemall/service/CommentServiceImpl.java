package com.pandax.litemall.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pandax.litemall.bean.Comment;
import com.pandax.litemall.bean.CommentExample;
import com.pandax.litemall.bean.WxCommentData;
import com.pandax.litemall.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentMapper commentMapper;
    @Override
    public int getComments(int valueId, byte type) {
        return (int)commentMapper.getComments(valueId,type);
    }

    @Override
    public int getPicComments(int valueId, byte type) {
        return (int) commentMapper.getPicComments(valueId, type);
    }

    @Override
    public List<Comment> getCommentsList(int valueId,byte type,int size,int page) {
        PageHelper.startPage(page,size);
        CommentExample commentExample = new CommentExample();
        CommentExample.Criteria criteria = commentExample.createCriteria();
        criteria.andValueIdEqualTo(valueId).andTypeEqualTo(type);
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        PageInfo<Comment> commentPageInfo = new PageInfo<>(comments);
        return comments;

    }

    @Override
    public Map getPicCommentsList(int valueId, byte type, int size, int page) {
        PageHelper.startPage(page,size);
        List<WxCommentData> picCommentsList = commentMapper.getPicCommentsList(valueId, type);
        PageInfo<WxCommentData> commentPageInfo = new PageInfo<>(picCommentsList);
        return null;

    }
}
