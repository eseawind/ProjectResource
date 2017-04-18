package com.shlanbao.tzsc.wct.msg.warn.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.wct.msg.warn.beans.MsgInfoBean;



public interface WctMsgInfoServiceI {
	/**
	 *查询系统通知信息 
	 */
	public List<MsgInfoBean> getAll();
	/**
	 *查询所有系统通知信息 
	 */
	public DataGrid getInfoList(MsgInfoBean infoBean,int  pageIndex,HttpSession session) throws Exception;
	/**
	 *取消系统通知信息 
	 */
	public void updateInfoFlagAndFour(String id);
}
