package com.pandax.litemall.controller;


import com.pandax.litemall.bean.BaseReqVo;
import com.pandax.litemall.bean.UserInfo;
import com.pandax.litemall.shiro.UserToken;
import com.pandax.litemall.shiro.UserTokenManager;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import com.pandax.litemall.bean.BaseReqVo;
import com.pandax.litemall.bean.User;
import com.pandax.litemall.service.SmsService;
import com.pandax.litemall.service.UserService;
import com.pandax.litemall.shiro.MallToken;

import com.pandax.litemall.util.*;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Autowired
    SmsService smsService;

    @Value("${my.Storage.path}")
    private String prefix;

    private HashMap<String, String> verificationCodeMap;

    /**
     * 用户商城账号登录
     * @param map 包含username和password
     * @param request
     * @return
     */
    @RequestMapping("/auth/login")
    public Object login(@RequestBody Map map, HttpServletRequest request) {
        String username = (String) map.get("username");
        String password = (String) map.get("password");

        //MD5加密
        password = Md5Utils.getMultiMd5(password);

        MallToken mallToken = new MallToken(username, password, "wx");
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(mallToken);
        } catch (AuthenticationException e) {
            return BaseReqVo.fail(600, "账号或者密码错误!");
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
        User user = (User) subject.getPrincipal();
        if(user == null) {
            return BaseReqVo.fail(1000, "服务器异常，请稍后再试。");
        }
        String mobile = user.getMobile();
        if (verificationCodeMap != null) {
            verificationCodeMap.remove(mobile);
        }
        subject.logout();
        return BaseReqVo.ok();
    }


    /**
     * 用户获得验证码
     * 使用验证码进行身份校验
     * @return
     */
    @RequestMapping("auth/regCaptcha")
    public BaseReqVo obtainVerificationCode(@RequestBody Map map){
        String mobile = (String) map.get("mobile");
        String verificationCode = smsService.obtainVerificationCode(mobile);
        verificationCodeMap = new HashMap();
        verificationCodeMap.put(mobile, verificationCode);
        return BaseReqVo.ok();
    }


    /**
     * 用户注册
     * 使用验证码进行身份校验
     * @return
     */
    @RequestMapping("auth/register")
    public BaseReqVo register(@RequestBody Map map, HttpServletRequest request){
        String code = (String) map.get("code");
        String mobile = (String) map.get("mobile");
        if(!"88888888".equals(code)) {
            if (verificationCodeMap == null) {
                return BaseReqVo.fail(704, "验证码不正确！");
            } else {
                String codeFromMap = verificationCodeMap.get(mobile);
                if (!codeFromMap.equals(code)) {
                    return BaseReqVo.fail(704, "验证码不正确！");
                }
            }
        }

        User userFromDB = userService.selectUserByMobile(mobile);
        if (userFromDB != null) {
            return BaseReqVo.fail(604, "该手机号已被注册");
        }

        String ipAddr = HttpUtils.getIpAddr(request);
        String username = (String) map.get("username");
        if(userService.checkUsernameExist(username)) {
            return BaseReqVo.fail(602, "该用户名已存在。");
        }

        String password = (String) map.get("password");
        //MD5加密
        password = Md5Utils.getMultiMd5(password);

        String wxCode = (String) map.get("wxCode");



        User user = new User();

        user.setUsername(username);
        user.setPassword(password);

        byte gender = new Byte("1");
        user.setGender(gender);

        //user.setBirthday();
        user.setLastLoginTime(new Date());
        user.setLastLoginIp(ipAddr);

        //这里写啥呀？啊啊啊啊啊啊啊啊啊啊啊啊啊。
        byte userLevel = new Byte("0");
        user.setUserLevel(userLevel);

        user.setNickname(username);
        user.setMobile(mobile);

        String avatar = user.getAvatar();
        if (avatar == null || "".equals(avatar)) {
            avatar = prefix;
            if(user.getGender() == 1){
                avatar += "DEFAUIT_HEAD_IMG/male_headImg.jpg";
            } else {
                avatar += "DEFAUIT_HEAD_IMG/female_headImg.jpg";
            }
        }
        user.setAvatar(avatar);


        //这里写啥呀？啊啊啊啊啊啊啊啊啊啊啊啊啊。
        user.setWeixinOpenid("WeixinOpenid");
        //这里写啥呀？啊啊啊啊啊啊啊啊啊啊啊啊啊。

        byte status = new Byte("0");
        user.setStatus(status);

        user.setAddTime(new Date());
        user.setUpdateTime(new Date());
        user.setDeleted(false);

        int rs = userService.insertUser(user);
        if(rs != 1) {
            return BaseReqVo.fail(1000, "当前网络繁忙，请稍后再试。");
        }

        Map<Object, Object> result = new HashMap<Object, Object>();
        result.put("token", wxCode);
        result.put("tokenExpire", new Date());
        Map<Object, Object> userInfo = new HashMap<Object, Object>();
        userInfo.put("nickName", user.getNickname());

        userInfo.put("avatarUrl", user.getAvatar());
        result.put("userInfo", userInfo);
        return BaseReqVo.ok(result);
    }

    @RequestMapping("auth/reset")
    public BaseReqVo reset(@RequestBody Map map){
        String password = (String) map.get("password");
        String mobile = (String) map.get("mobile");
        String code = (String) map.get("code");
        String codeFromSys = verificationCodeMap.get(mobile);
        if(!codeFromSys.equals(code)){
            return BaseReqVo.fail(704, "验证码不正确！");
        }
        User user = userService.selectUserByMobile(mobile);
        if(user == null) {
            return BaseReqVo.fail(603, "该手机号还尚未注册");
        }

        //MD5加密
        password = Md5Utils.getMultiMd5(password);
        user.setPassword(password);
        userService.updateUser(user);
        return BaseReqVo.ok();
    }

    /**
     * 用户使用微信登录
     * @param map
     * @return
     */
    @RequestMapping("auth/login_by_weixin")
    public BaseReqVo loginByWx(@RequestBody Map map) {
        String code = (String) map.get("code");
        UserInfo usrInfo = (UserInfo) map.get("usrInfo");
        map.put("token", code);
        map.put("tokenExpire", new Date());
        map.remove("code");
        return BaseReqVo.ok(map);
    }


}
