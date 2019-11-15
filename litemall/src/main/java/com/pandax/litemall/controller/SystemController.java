package com.pandax.litemall.controller;

import com.pandax.litemall.bean.BaseReqVo;
import com.pandax.litemall.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@Controller
@RequestMapping("admin")
@ResponseBody
public class SystemController {

    @Autowired
    AdminService adminService;

    /**
     * 显示所有管理员账号以及其详情
     * @param page 页数
     * @param limit 每页数量
     * @param sort  分类情况
     * @param order 排序情况
     * @return
     */
    @RequestMapping("admin/list")
    public BaseReqVo showAdmins(Integer page, Integer limit, String sort, String order){
        BaseReqVo baseReqVo = new BaseReqVo();
        HashMap<String, Object> map = adminService.queryUsers();
        baseReqVo.setData(map);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    @RequestMapping("admin/list")
    public BaseReqVo adminOptions(Integer page, Integer limit, String sort, String order){
        BaseReqVo baseReqVo = new BaseReqVo();

        return baseReqVo;
    }
}
