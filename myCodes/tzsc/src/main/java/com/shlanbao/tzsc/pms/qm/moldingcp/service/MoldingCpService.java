package com.shlanbao.tzsc.pms.qm.moldingcp.service;

import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.qm.moldingcp.beans.MoldingCpBean;
/**
 * 成型车间成品物理检验记录接口
 * <li>@author luther.zhang
 * <li>@create 2015-02-27
 */
public interface MoldingCpService {
	/**
	 * 查询
	 */
	DataGrid queryList(MoldingCpBean bean,PageParams pageParams) throws Exception;
}
