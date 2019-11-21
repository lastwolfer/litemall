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


    @RequestMapping("wx/comment/count")
    public BaseReqVo commentCount(int valueId,byte type){
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
    //{
//    "data": {
//        "data": [
//            {
//   2             "id": 241,  2
//   2             "valueId": 1009024,
//   2             "type": 0,
//   1             "content": "好舒服！可以变换各种形状！比我之前在朋友家看到的懒人沙发好很多啊！",
//       1         "userId": 1,
//                "hasPicture": true,
//        1        "picUrls": ["https://yanxuan.nosdn.127.net/a3bd6f130fe3ee340037a37b6c721d16.jpg"],
//                "star": 1,
//        1        "addTime": "2018-01-04 16:00:00",
//                "updateTime": "2018-01-04 16:00:00",
//                "deleted": false
//            }
//        ],
//        "count": 20,
//        "currentPage": 1
//    },
//    "errmsg": "成功",
//    "errno": 0
//}
    @RequestMapping("wx/comment/list")
    public BaseReqVo commentList(int valueId,byte type,int size,int page,int showType){
        PageHelper pageHelper = new PageHelper();
        List<Comment> comments = null;
        if (showType == 0) {
            comments = commentService.getCommentsList(valueId,type,size,page);
        }else {
            Map result = commentService.getPicCommentsList(valueId,type,size,page);
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
