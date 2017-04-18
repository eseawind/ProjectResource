package com.shlanbao.tzsc.pms.qm.packagecp.service;

import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.qm.packagecp.beans.PackageCpBean;
/**
 * 卷包车间成品物理检验记录接口
 * <li>@author luther.zhang
 * <li>@create 2015-02-26
 */
public interface PackageCpService {
	/**
	 * 查询
	 */
	DataGrid queryList(PackageCpBean bean,PageParams pageParams) throws Exception;
}
