package com.pandax.litemall.controller;


import com.pandax.litemall.utils.BaseRespVo;
import com.pandax.litemall.utils.UserInfo;
import com.pandax.litemall.utils.UserToken;
import com.pandax.litemall.utils.UserTokenManager;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by little Stone
 * Date 2019/7/8 Time 20:55
 */
@RestController
@RequestMapping
public class WxAuthController {

	@RequestMapping("wx/auth/login")
	@ResponseBody
	public Object login(@RequestBody String username, HttpServletRequest request) {
		/*String username = JacksonUtil.parseString(body, "username");
		String password = JacksonUtil.parseString(body, "password");*/

		//*******************************
		//根据username和password查询user信息
		//*******************************

		// userInfo
		UserInfo userInfo = new UserInfo();
		userInfo.setNickName(username);
		//userInfo.setAvatarUrl(user.getAvatar());


		//********************************
		//根据获得userid
		int userId = 1;
		//********************************
		// token
		UserToken userToken = UserTokenManager.generateToken(userId);

		Map<Object, Object> result = new HashMap<Object, Object>();
		result.put("token", userToken.getToken());
		result.put("tokenExpire", userToken.getExpireTime().toString());
		result.put("userInfo", userInfo);
		return BaseRespVo.ok(result);
	}

	@GetMapping("user/index")
	public Object list(HttpServletRequest request) {
		//前端写了一个token放在请求头中
		//*************************
		//获得请求头
		String tokenKey = request.getHeader("X-cskaoyanmall-Admin-Token");
		Integer userId = UserTokenManager.getUserId(tokenKey);
		//通过请求头获得userId，进而可以获得一切关于user的信息
		//**************************
		if (userId == null) {
			return BaseRespVo.fail();
		}

		Map<Object, Object> data = new HashMap<Object, Object>();
		//***********************************
		//根据userId查询订单信息
		data.put("order", null);
		//***********************************

		return BaseRespVo.ok(data);
	}
}
