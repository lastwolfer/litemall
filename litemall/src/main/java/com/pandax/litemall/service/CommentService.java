package com.pandax.litemall.service;

import com.pandax.litemall.bean.Comment;
import com.pandax.litemall.bean.WxCommentData;

import java.util.List;
import java.util.Map;

public interface CommentService {
    int getComments(int valueId, byte type);

    int getPicComments(int valueId, byte type);


    List<WxCommentData> getCommentsList(int valueId, byte type, int size, int page,int showType);

    int commentPost(Comment comment);
}
