package com.shlanbao.tzsc.pms.qm.avgrange.service;

import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.qm.avgrange.beans.AvgRangeBean;
import com.shlanbao.tzsc.pms.qm.avgrange.beans.AvgRangeQueryBean;
/**
 * 平均值-极差控制图分析接口
 * <li>@author luther.zhang
 * <li>@create 2015-03-02
 */
public interface AvgRangeService {
	/**
	 * 查询
	 */
	DataGrid queryList(AvgRangeQueryBean bean,PageParams pageParams) throws Exception;
	/**
	 * 查询统计信息
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public AvgRangeBean getAvgRangeBean(AvgRangeQueryBean bean) throws Exception;
}
