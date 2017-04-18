package com.shlanbao.tzsc.pms.sys.resource.beans;
/**
 * 收藏bean
 * <li>@author Leejean
 * <li>@create 2014年8月22日下午7:33:17
 */
public class FavoriteBean {
	private String id;
	private String uid;//用户id
	private String rid;//目录id
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getRid() {
		return rid;
	}
	public void setRid(String rid) {
		this.rid = rid;
	}
}
