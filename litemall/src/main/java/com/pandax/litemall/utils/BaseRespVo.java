package com.pandax.litemall.utils;



/**
 * Created by little Stone
 * Date 2019/4/19 Time 10:16
 */
public class BaseRespVo {
	Object data;
	String errmsg;
	Integer errno;



	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public Integer getErrno() {
		return errno;
	}

	public void setErrno(Integer errno) {
		this.errno = errno;
	}

	public static BaseRespVo ok(Object data){
		BaseRespVo baseRespVo = new BaseRespVo();
		baseRespVo.setData(data);
		baseRespVo.setErrmsg("成功");
		baseRespVo.setErrno(0);
		return baseRespVo;
	}

	public static BaseRespVo fail() {
		BaseRespVo baseRespVo = new BaseRespVo();
		baseRespVo.setErrno(-1);
		baseRespVo.setErrmsg("错误");
		return baseRespVo;
	}
	public static BaseRespVo fail(Integer errorNo, String errorMsg) {
		BaseRespVo baseRespVo = new BaseRespVo();
		baseRespVo.setErrno(errorNo);
		baseRespVo.setErrmsg(errorMsg);
		return baseRespVo;
	}
	public static BaseRespVo updatedDataFailed() {
		return fail(505, "更新数据失败");
	}
	public static BaseRespVo createDataFailed() {
		return fail(510, "创建数据失败");
	}

	public static BaseRespVo badArgument() {
		return fail(401, "参数不对");
	}

}
