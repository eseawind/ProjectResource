package com.lanbao.dws.common.tools;

/**
 * json数据
 * @author shisihai
 *
 */
public class JsonBean {
	private boolean flag=false;
	private String msg;
	private Object obj;
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
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
	
}
