package com.pandax.litemall.controller;

import com.github.pagehelper.PageHelper;
import com.pandax.litemall.bean.BaseReqVo;
import com.pandax.litemall.bean.Comment;
import com.pandax.litemall.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CommentController {
    @Autowired
    CommentService commentService;

    //http://192.168.2.100:8081/wx/comment/count?valueId=1114011&type=0
    //{"errno":0,"data":{"hasPicCount":1,"allCount":2},"errmsg":"成功"}
    @RequestMapping("wx/comment/count")
    public BaseReqVo commentCount(int valueId,int type){
        int i = commentService.getComments(valueId,type);
        int j = commentService.getPicComments(valueId,type);
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        Map<String, Object> map = new HashMap<>();
        map.put("hasPicCount", i);
        map.put("allCount", j);
        baseReqVo.setErrno(0);
        baseReqVo.setData(map);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }

    @RequestMapping("wx/comment/list")
    public BaseReqVo commentList(int valueId,int type,int size,int page,int showType){
        PageHelper pageHelper = new PageHelper();
        List<Comment> comments = null;
        if (showType == 0) {
            comments = commentService.getCommentsList(valueId,type,size,page);
        }else {
            comments = commentService.getPicCommentsList(valueId,type,size,page);
        }
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        Map<String, Object> map = new HashMap<>();
        map.put("data", comments);
        map.put("count", comments.size());
        map.put("currentPage", page);
        baseReqVo.setErrno(0);
        baseReqVo.setData(map);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }

}
