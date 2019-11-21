package com.pandax.litemall.controller;

import com.pandax.litemall.bean.BaseReqVo;
import com.pandax.litemall.bean.UserInfo;
import com.pandax.litemall.shiro.UserToken;
import com.pandax.litemall.shiro.UserTokenManager;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import com.pandax.litemall.bean.BaseReqVo;
import com.pandax.litemall.bean.User;
import com.pandax.litemall.service.UserService;
import com.pandax.litemall.shiro.MallToken;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
<<<<<<< HEAD
 * Created by little Stone
 * Date 2019/7/8 Time 20:55
 */


@RestController
@RequestMapping("/wx")
public class WxAuthController {

	@GetMapping("user/index")
	public Object list(HttpServletRequest request) {
		//前端写了一个token放在请求头中
		//*************************
		//获得请求头
		String tokenKey = request.getHeader("X-Litemall-Token");
		Integer userId = UserTokenManager.getUserId(tokenKey);
		//通过请求头获得userId，进而可以获得一切关于user的信息
		//**************************
		if (userId == null) {
			return BaseReqVo.fail();
		}

		Map<Object, Object> data = new HashMap<Object, Object>();
		//***********************************
		//根据userId查询订单信息
		data.put("order", null);
		//***********************************

		return BaseReqVo.ok(data);
	}

    @Autowired
    UserService userService;

    /**
     * 用户商城账号登录
     * @param map 包含username和password
     * @param request
     * @return
     */
    @RequestMapping("/auth/login")
    @ResponseBody
    public Object login(@RequestBody Map map, HttpServletRequest request) {
        String username = (String) map.get("username");
        String password = (String) map.get("password");

        MallToken mallToken = new MallToken(username, password, "wx");
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(mallToken);
        } catch (AuthenticationException e) {
            return BaseReqVo.fail(1000, "账号或者密码错误!");
        }

        User user = userService.selectUserByUsername(username);

        Map<Object, Object> result = new HashMap<Object, Object>();
        result.put("token", subject.getSession().getId());
        result.put("tokenExpire", new Date());
        Map<Object, Object> userInfo = new HashMap<Object, Object>();
        userInfo.put("nickName", user.getNickname());
        userInfo.put("avatarUrl", user.getAvatar());
        result.put("userInfo", userInfo);
        return BaseReqVo.ok(result);
    }


    /**
     * 用户登出
     * @return
     */
    @RequestMapping("/auth/logout")
    @ResponseBody
    public Object logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return BaseReqVo.ok();
    }

    //@RequestMapping("auth/regCaptcha")


}
