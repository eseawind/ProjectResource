package com.shlanbao.tzsc.pms.qm.passrate.service;

import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.qm.passrate.beans.PassRateBean;
import com.shlanbao.tzsc.pms.qm.passrate.beans.PassRateQueryBean;
/**
 * 合格率统计接口
 * <li>@author luther.zhang
 * <li>@create 2015-03-02
 */
public interface PassRateService {
	/**
	 * 查询
	 */
	DataGrid queryList(PassRateQueryBean bean,PageParams pageParams) throws Exception;
	/**
	 * 查询统计信息
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public PassRateBean getPassRateBean(PassRateQueryBean bean) throws Exception;
}
