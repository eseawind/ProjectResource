package com.shlanbao.tzsc.pms.qm.cpk.service;

import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.qm.cpk.beans.CpkBean;
import com.shlanbao.tzsc.pms.qm.cpk.beans.CpkQueryListBean;
/**
 * CPK统计接口
 * <li>@author luther.zhang
 * <li>@create 2015-03-03
 */
public interface CpkService {
	/**
	 * 查询
	 */
	DataGrid queryList(CpkQueryListBean bean,PageParams pageParams) throws Exception;
	/**
	 * 查询统计信息
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public CpkBean getCpkBean(CpkQueryListBean bean) throws Exception;
}
