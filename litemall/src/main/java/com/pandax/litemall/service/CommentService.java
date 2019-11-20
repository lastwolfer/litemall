package com.pandax.litemall.service;

import com.pandax.litemall.bean.Comment;

import java.util.List;

public interface CommentService {
    int getComments(int valueId, int type);

    int getPicComments(int valueId, int type);

    List<Comment> getCommentsList(int valueId,int type,int size,int page);

    List<Comment> getPicCommentsList(int valueId, int type, int size, int page);
}
