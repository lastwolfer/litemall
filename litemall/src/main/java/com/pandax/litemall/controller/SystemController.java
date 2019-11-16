package com.pandax.litemall.controller;

import com.pandax.litemall.bean.Admin;
import com.pandax.litemall.bean.BaseReqVo;
import com.pandax.litemall.bean.Log;
import com.pandax.litemall.bean.RoleInfo;
import com.pandax.litemall.service.AdminService;
import com.pandax.litemall.service.LogService;
import com.pandax.litemall.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("admin")
public class SystemController {

    @Autowired
    AdminService adminService;

    @Autowired
    RoleService roleService;

    @Autowired
    LogService logService;

    /**
     * 显示所有管理员账号以及其详情
     *
     * @param page  页数
     * @param limit 每页数量
     * @param sort  分类情况
     * @param order 排序情况
     * @return
     */
    @RequestMapping("admin/list")
    public BaseReqVo showAdmins(Integer page, Integer limit,
                                String sort, String order, String username) {
        BaseReqVo baseReqVo = new BaseReqVo();
        HashMap<String, Object> map = adminService.queryUsers(page, limit, sort, order, username);
        baseReqVo.setData(map);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    /**
     * 显示所有管理员称号
     *
     * @return options数组，以及基本信息
     */
    @RequestMapping("role/options")
    public BaseReqVo adminOptions() {
        BaseReqVo baseReqVo = new BaseReqVo();
        List<RoleInfo> roleList = roleService.selectRoles();
        baseReqVo.setData(roleList);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    /**
     * 更新管理员信息
     * @param admin 管理员信息
     * @return
     */
    @RequestMapping("admin/update")
    public BaseReqVo adminUpdate(@RequestBody Admin admin){
        BaseReqVo baseReqVo = new BaseReqVo();
        String password = admin.getPassword();
        if(password.length() < 6){
            baseReqVo.setData(null);
            baseReqVo.setErrno(602);
            baseReqVo.setErrmsg("管理员密码长度不能小于6");
            return baseReqVo;
        }
        adminService.updateAdmin(admin);
        baseReqVo.setData(admin);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);


        //日志操作
        Log log = new Log("unknow admin", "0:0:0:0:0:0:0:1",
                1, "编辑管理员");

        /*log.setAdmin(username);
        log.setIp("0:0:0:0:0:0:0:1");
        log.setType(1);
        log.setAction("编辑管理员");
        log.setStatus(true);
        log.setResult("");
        log.setComment("");
        log.setAddTime(new Date());
        log.setUpdateTime(new Date());
        log.setDeleted(false);*/

        logService.record(log);

        return baseReqVo;
    }

    /**
     * 删除管理员
     * @param admin 管理员信息
     * @return
     */
    @RequestMapping("admin/delete")
    public BaseReqVo deleteAdmin(@RequestBody Admin admin){
        BaseReqVo baseReqVo = new BaseReqVo();
        adminService.deleteAdmin(admin);

        //日志操作
        Log log = new Log("unknow admin", "0:0:0:0:0:0:0:1",
                1, "删除管理员");

        logService.record(log);

        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    @RequestMapping("log/list")
    public BaseReqVo showLogList(Integer page, Integer limit,
                                 String sort, String order,  String name){
        BaseReqVo baseReqVo = new BaseReqVo();
        Map map = logService.showLogList(page,limit,sort,order, name);
        baseReqVo.setData(map);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }
}
