package com.pandax.litemall.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pandax.litemall.bean.Comment;
import com.pandax.litemall.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentMapper commentMapper;
    @Override
    public int getComments(int valueId, int type) {
        return (int)commentMapper.getComments(valueId,type);
    }

    @Override
    public int getPicComments(int valueId, int type) {
        return (int) commentMapper.getPicComments(valueId, type);
    }

    @Override
    public List<Comment> getCommentsList(int valueId,int type,int size,int page) {
        PageHelper.startPage(page,size);
        List<Comment> comments = commentMapper.getCommentsList(valueId, type);
        PageInfo<Comment> commentPageInfo = new PageInfo<>(comments);
        return comments;

    }

    @Override
    public List<Comment> getPicCommentsList(int valueId, int type, int size, int page) {
        PageHelper.startPage(page,size);
        List<Comment> comments = commentMapper.getPicCommentsList(valueId, type);
        PageInfo<Comment> commentPageInfo = new PageInfo<>(comments);
        return comments;

    }
}
