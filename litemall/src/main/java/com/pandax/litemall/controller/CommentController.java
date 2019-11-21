package com.pandax.litemall.controller;

import com.github.pagehelper.PageHelper;
import com.pandax.litemall.bean.BaseReqVo;
import com.pandax.litemall.bean.Comment;
import com.pandax.litemall.bean.User;
import com.pandax.litemall.bean.WxCommentData;
import com.pandax.litemall.service.CommentService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CommentController {
    @Autowired
    CommentService commentService;


    @RequestMapping("wx/comment/count")
    public BaseReqVo commentCount(int valueId,byte type){
        int i = commentService.getComments(valueId,type);
        int j = commentService.getPicComments(valueId,type);
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        Map<String, Object> map = new HashMap<>();
        map.put("hasPicCount", j);
        map.put("allCount", i);
        baseReqVo.setErrno(0);
        baseReqVo.setData(map);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }

    @RequestMapping("wx/comment/list")
    public BaseReqVo commentList(int valueId,byte type,int size,int page,int showType){
        List<WxCommentData> comments = commentService.getCommentsList(valueId,type,size,page,showType);
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

    @RequestMapping("wx/comment/post")
    public BaseReqVo commentPost(@RequestBody Comment comment) throws ParseException {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = df.parse(df.format(new Date()));
        comment.setUserId(user.getId());
        comment.setAddTime(date);
        comment.setUpdateTime(date);
        int i = commentService.commentPost(comment);
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        Map<String, Object> map = new HashMap<>();
        map.put("data", comment);
        baseReqVo.setErrno(0);
        baseReqVo.setData(map);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }

}
