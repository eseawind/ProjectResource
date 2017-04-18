package com.shlanbao.tzsc.base.model;

import com.alibaba.fastjson.JSONObject;

/**
 * JSON模型
 * <li>@author Leejean
 * <li>@create 2014-6-24 下午04:19:00
 */
@SuppressWarnings("serial")
public class Json implements java.io.Serializable {

	private boolean success = false;

	private String msg = "系统异常,请稍后再试.";

	private Object obj = null;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}
	public String toJSONString(){
		JSONObject json=new JSONObject();
		json.put("success",this.success);
		json.put("msg", msg);
		json.put("obj",obj);
		return json.toString();
	}
}
