package com.shlanbao.tzsc.pms.qm.shape.service;

import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.qm.shape.beans.ShapeQueryListBean;
import com.shlanbao.tzsc.pms.qm.shape.beans.ShapeStatisticsBean;
/**
 * 外观质量统计接口
 * <li>@author luther.zhang
 * <li>@create 2015-02-28
 */
public interface ShapeService {
	/**
	 * 查询
	 */
	DataGrid queryList(ShapeQueryListBean bean,PageParams pageParams) throws Exception;
	/**
	 * 查询统计信息
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public ShapeStatisticsBean getStatisticsBean(ShapeQueryListBean bean) throws Exception;
}
