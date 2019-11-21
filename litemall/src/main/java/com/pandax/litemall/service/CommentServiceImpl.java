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
    public List<WxCommentData> getCommentsList(int valueId, byte type, int size, int page,int showType) {
        PageHelper.startPage(page,size);
        List<WxCommentData> comments = commentMapper.getPicCommentsList(valueId, type,showType);
        PageInfo<WxCommentData> commentPageInfo = new PageInfo<>(comments);
        return comments;

    }
}
