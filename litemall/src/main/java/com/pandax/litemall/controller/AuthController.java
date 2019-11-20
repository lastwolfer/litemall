package com.pandax.litemall.controller;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.pandax.litemall.bean.Admin;
import com.pandax.litemall.bean.BaseReqVo;
import com.pandax.litemall.bean.InfoData;
import com.pandax.litemall.bean.LoginVo;
import com.pandax.litemall.mapper.PermissionMapper;
import com.pandax.litemall.service.LogService;
import com.pandax.litemall.service.PermissionService;
import com.pandax.litemall.service.RoleService;
import com.pandax.litemall.shiro.MallToken;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    RoleService roleService;

    @Autowired
    PermissionService permissionService;

    @RequestMapping("admin/auth/login")
    public BaseReqVo login(@RequestBody LoginVo loginVo) {
        BaseReqVo baseReqVo = new BaseReqVo();
        Subject subject = SecurityUtils.getSubject();
        //admin得小写
        MallToken mallToken = new MallToken(loginVo.getUsername(), loginVo.getPassword(), "admin");
        //里面放自己定义的Token
        try {
            subject.login(mallToken);
        } catch (AuthenticationException e) {
            baseReqVo.setErrmsg("账号或密码错误");
            baseReqVo.setErrno(1000);
            return baseReqVo;
            //e.printStackTrace();
        }
        Serializable id = subject.getSession().getId();
        //baseReqVo.setData("4b7d719e-53b7-4019-9677-6309b2445b45");
        baseReqVo.setData(id);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    @RequestMapping("admin/auth/info")
    //@RequiresPermissions("admin:auth:info")
    public BaseReqVo info(String token){
        BaseReqVo baseReqVo = new BaseReqVo();
        Subject subject = SecurityUtils.getSubject();
        Admin admin = (Admin) subject.getPrincipal();
        InfoData data = new InfoData();
        //data.setAvatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        data.setAvatar(admin.getAvatar());
        data.setName(admin.getUsername());
        Integer[] roleIds = admin.getRoleIds();

        HashSet<String> urllist = permissionService.selectSysPermissions();
        HashSet<String> perms = permissionService.selectPermissionsByRoleId(roleIds);
        ArrayList<String> arrayList = new ArrayList<>();
        for (String s : urllist) {
            String[] split = s.split("/");
            StringBuilder sb = new StringBuilder();
            for (int i = 1; i < split.length; i++) {
                sb.append(split[i]).append(":");
            }
            sb.delete(sb.length() - 1, sb.length());
            if(perms.contains(sb.toString())) {
                arrayList.add(s);
            }
        }
        data.setPerms(arrayList);

        //开发时候使用！！！！
        //ArrayList<String> list = new ArrayList<>();

        //一定要 "GET /admin/order/list", "POST /admin/ad/create" 格式
        /*list.add("/admin/order/list");
        list.add("/admin/ad/create");
        list.add("/admin/topic/create");
        data.setPerms(list);*/
        /*if("admin123".equals(admin.getUsername())){
            ArrayList<String> list = new ArrayList<>();
            list.add("*");
            data.setPerms(list);
        }*/

        List<String> rolesList = roleService.getRolesById(roleIds);
        data.setRoles(rolesList);

        baseReqVo.setData(data);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);


        return baseReqVo;
    }


    @RequestMapping("admin/auth/logout")
    public BaseReqVo logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }


    @RequestMapping("admin/auth/fail")
    public BaseReqVo authenticationFail(){
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrno(600);
        baseReqVo.setErrmsg("认证失败，请重试。");
        return baseReqVo;
    }
}
