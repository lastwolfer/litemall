package com.pandax.litemall.controller;


import com.pandax.litemall.bean.BaseReqVo;
import com.pandax.litemall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    /**
     * 查詢所有的用戶
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @param mobile
     * @param username
     * @return
     */
    @RequestMapping("/admin/user/list")
    public BaseReqVo userList(Integer page, Integer limit, String sort, String order,String mobile,String username){
        BaseReqVo baseReqVo =new BaseReqVo();
        Map map = userService.selectUserList(page, limit, sort, order,mobile,username);
        baseReqVo.setErrno(0);
        baseReqVo.setData(map);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }


    @RequestMapping("/admin/address/list")
    public BaseReqVo addressList(Integer page, Integer limit, String sort, String order,String name,Integer userId){
        BaseReqVo baseReqVo =new BaseReqVo();
        Map map = userService.selectAddressList(page, limit, sort, order,name,userId);
        baseReqVo.setErrno(0);
        baseReqVo.setData(map);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }

    @RequestMapping("/admin/collect/list")
    public BaseReqVo collectList(Integer page, Integer limit, String sort, String order,Integer valueId,Integer userId){
        BaseReqVo baseReqVo =new BaseReqVo();
        Map map = userService.selectCollectList(page, limit, sort, order,valueId,userId);
        baseReqVo.setErrno(0);
        baseReqVo.setData(map);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }

    @RequestMapping("/admin/footprint/list")
    public BaseReqVo footprintList(Integer page, Integer limit, String sort, String order,Integer goodsId,Integer userId){
        BaseReqVo baseReqVo =new BaseReqVo();
        Map map = userService.selectFootprintList(page, limit, sort, order,goodsId,userId);
        baseReqVo.setErrno(0);
        baseReqVo.setData(map);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }

    @RequestMapping("/admin/history/list")
    public BaseReqVo historyList(Integer page, Integer limit, String sort, String order,Integer userId,String keyword){
        BaseReqVo baseReqVo =new BaseReqVo();
        Map map = userService.selectHistoryList(page, limit, sort, order,userId,keyword);
        baseReqVo.setErrno(0);
        baseReqVo.setData(map);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }

    @RequestMapping("/admin/feedback/list")
    public BaseReqVo feedbackList(Integer page, Integer limit, String sort, String order,Integer id,String username){
        BaseReqVo baseReqVo =new BaseReqVo();
        Map map = userService.selectFeedbackList(page, limit, sort, order,id,username);
        baseReqVo.setErrno(0);
        baseReqVo.setData(map);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }
}
