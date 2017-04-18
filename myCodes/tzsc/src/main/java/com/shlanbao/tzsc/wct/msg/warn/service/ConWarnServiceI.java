package com.shlanbao.tzsc.wct.msg.warn.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.wct.msg.warn.beans.ConWarnBean;

public interface ConWarnServiceI {
	/**
	 * 传过来机台名字去查数据库，因为添加警告保存了机台信息
	 */
	public List<ConWarnBean> getByEquipmentName(String name);
	/**
	 * 实现查询所有的单耗信息
	 */
	public DataGrid getConList(ConWarnBean bean ,int pageIndex,HttpSession session)throws Exception;
	/**
	 * 取消警告
	 */
	public void updateConAndStsTwo(String id);
}
