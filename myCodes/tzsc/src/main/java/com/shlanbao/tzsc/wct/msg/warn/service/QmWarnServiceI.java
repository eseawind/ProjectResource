package com.shlanbao.tzsc.wct.msg.warn.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.wct.msg.warn.beans.QmWarnBean;

public interface QmWarnServiceI {
	/**
	 *根据机台name查询机台质量告警 
	 */
	public List<QmWarnBean> getAll(String name);
	/**
	 *查询机台质量告警 
	 */
	public DataGrid getAll(QmWarnBean qmWarnBean,int  pageIndex,HttpServletRequest httpServletRequest) throws Exception;
	/**
	 *取消警告
	 */
	public void updateQmStsAndTWO(String id);
}
