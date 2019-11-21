package com.pandax.litemall.controller;

import com.github.pagehelper.PageHelper;
import com.pandax.litemall.bean.BaseReqVo;
import com.pandax.litemall.bean.Comment;
import com.pandax.litemall.bean.WxCommentData;
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
//{
//    "errno": 0,
//    "data": {
//        "data": [{
//            "userInfo": {
//                "nickName": "dr lan",
//                "avatarUrl": ""
//            },
//            "addTime": "2019-11-20 04:22:03",
//            "picList": ["http://192.168.2.100:8081/wx/storage/fetch/6cyaeo310obzfl96c0ck.png"],
//            "content": "勉勉强强，下次再来（滑稽）\n"
//        }],
//        "count": 1,
//        "currentPage": 1
//    },
//    "errmsg": "成功"
//}
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

}
