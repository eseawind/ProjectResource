package com.shlanbao.tzsc.pms.qm.better.service;

import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.qm.better.beans.BetterManageBean;
import com.shlanbao.tzsc.pms.qm.better.beans.BetterManageQueryBean;
/**
 * 质量改善管理接口
 * <li>@author luther.zhang
 * <li>@create 2015-03-10
 */
public interface BetterManageService {
	/**
	 * 查询
	 */
	DataGrid queryList(BetterManageQueryBean bean,PageParams pageParams) throws Exception;
	/**
	 * 查询统计信息
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public BetterManageBean getBeanData(BetterManageQueryBean bean) throws Exception;
}
