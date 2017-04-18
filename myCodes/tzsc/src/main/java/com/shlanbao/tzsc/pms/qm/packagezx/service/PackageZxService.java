package com.shlanbao.tzsc.pms.qm.packagezx.service;

import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.qm.packagezx.beans.PackageZxBean;
/**
 * 卷包车间在线物理检验记录接口
 * <li>@author luther.zhang
 * <li>@create 2015-02-27
 */
public interface PackageZxService {
	/**
	 * 查询
	 */
	DataGrid queryList(PackageZxBean bean,PageParams pageParams) throws Exception;
}
