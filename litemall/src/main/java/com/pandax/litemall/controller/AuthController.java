package com.pandax.litemall.controller;
import java.util.ArrayList;

import com.pandax.litemall.bean.BaseReqVo;
import com.pandax.litemall.bean.InfoData;
import com.pandax.litemall.bean.LoginVo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin/auth")
public class AuthController {

    @RequestMapping("login")
    public BaseReqVo login(@RequestBody LoginVo loginVo){
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setData("4b7d719e-53b7-4019-9677-6309b2445b45");
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    @RequestMapping("info")
    public BaseReqVo info(String token){
        BaseReqVo baseReqVo = new BaseReqVo();
        InfoData data = new InfoData();
        data.setAvatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        data.setName("songge");
        ArrayList<String> perms = new ArrayList<>();
        perms.add("*");
        data.setPerms(perms);
        ArrayList<String> roles = new ArrayList<>();
        roles.add("超级管理员");
        data.setRoles(roles);

        baseReqVo.setData(data);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);


        return baseReqVo;
    }
}
