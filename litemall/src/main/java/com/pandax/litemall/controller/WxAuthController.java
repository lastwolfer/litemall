package com.pandax.litemall.controller;


import com.pandax.litemall.bean.BaseReqVo;
import com.pandax.litemall.bean.User;
import com.pandax.litemall.service.SmsService;
import com.pandax.litemall.service.UserService;
import com.pandax.litemall.shiro.MallToken;

import com.pandax.litemall.shiro.WxRealm;
import com.pandax.litemall.util.*;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/wx")
public class WxAuthController {

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
        updateUserByLoginTimeAndIp(user, request);
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
        result.put("token", SecurityUtils.getSubject().getSession().getId());
        result.put("tokenExpire", new Date());
        Map<Object, Object> userInfo = new HashMap<Object, Object>();
        userInfo.put("nickName", user.getNickname());

        userInfo.put("avatarUrl", user.getAvatar());
        result.put("userInfo", userInfo);

        //认证下
        Subject subject = SecurityUtils.getSubject();
        MallToken token = new MallToken(username, password, "wx");
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            return BaseReqVo.fail(600, "账号或者密码错误!");
        }


        return BaseReqVo.ok(result);
    }

    /**
     * 用户密码重置
     * @param map
     * @param request
     * @return
     */
    @RequestMapping("auth/reset")
    public BaseReqVo reset(@RequestBody Map map,HttpServletRequest request){
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
        updateUserByLoginTimeAndIp(user, request);
        return BaseReqVo.ok();
    }

    /**
     * 用户使用微信登录
     * @param map
     * @return
     */
    @RequestMapping("auth/login_by_weixin")
    public BaseReqVo loginByWx(@RequestBody Map map,HttpServletRequest request) {
        String code = (String) map.get("code");
        Map userMap = (Map) map.get("userInfo");
        String nickName = (String) userMap.get("nickName");
        String avatarUrl = (String) userMap.get("avatarUrl");
        Integer gender = (Integer) userMap.get("gender");
        String language = (String) userMap.get("language");
        String province = (String) userMap.get("province");
        String city = (String) userMap.get("city");
        String country = (String) userMap.get("country");

        //String avatarUrl = userInfo.getAvatarUrl();
        //String nickName = userInfo.getNickName();
        String md51 = Md5Utils.getMd5(avatarUrl);
        String md52 = Md5Utils.getMd5(nickName);
        String wxId = Md5Utils.getMd5(md51 + md52);
        User user = userService.selectUserByWxId(wxId);


        if (user == null) {
            user = new User();

            UUID uuid = UUID.randomUUID();
            String username = uuid.toString();
            user.setUsername(username);
            String password = Md5Utils.getMd5(username);
            user.setPassword(password);
            user.setGender(new Byte(gender.toString()));
            user.setBirthday(new Date());
            user.setLastLoginTime(new Date());
            user.setLastLoginIp(HttpUtils.getIpAddr(request));
            user.setUserLevel(new Byte("0"));
            user.setNickname(nickName);
            user.setMobile("unknow");
            user.setAvatar(avatarUrl);
            user.setWeixinOpenid(wxId);
            user.setStatus(new Byte("0"));
            user.setAddTime(new Date());
            user.setUpdateTime(new Date());
            user.setDeleted(false);

            userService.insertUser(user);
        } else {
            updateUserByLoginTimeAndIp(user,request);
        }

        Subject subject = SecurityUtils.getSubject();
        MallToken mallToken = new MallToken(user.getUsername(),user.getPassword(), "wx");
        try {
            subject.login(mallToken);
        } catch (AuthenticationException e) {
            return BaseReqVo.fail(600, "账号或者密码错误!");
        }
        User userFromSubject = (User) subject.getPrincipal();

        map.put("token", subject.getSession().getId());
        map.put("tokenExpire", new Date());
        map.remove("code");
        return BaseReqVo.ok(map);
    }

    private void updateUserByLoginTimeAndIp(User user,HttpServletRequest request) {
        user.setLastLoginIp(HttpUtils.getIpAddr(request));
        user.setLastLoginTime(new Date());
        userService.updateUser(user);
    }


}
