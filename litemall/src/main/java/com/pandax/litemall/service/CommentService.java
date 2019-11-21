package com.pandax.litemall.service;

import com.pandax.litemall.bean.Comment;

import java.util.List;
import java.util.Map;

public interface CommentService {
    int getComments(int valueId, byte type);

    int getPicComments(int valueId, byte type);

    List<Comment> getCommentsList(int valueId,byte type,int size,int page);

    Map getPicCommentsList(int valueId, byte type, int size, int page);
}
