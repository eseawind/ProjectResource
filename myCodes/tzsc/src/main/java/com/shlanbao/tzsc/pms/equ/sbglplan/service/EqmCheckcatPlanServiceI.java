package com.shlanbao.tzsc.pms.equ.sbglplan.service;


import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.equ.sbglplan.beans.EqmSpotchRecodeBean;
/**
 * 
* @ClassName: EqmPlanServiceI 
* @Description: 设备轮保管理 
*
 */
public interface EqmCheckcatPlanServiceI {
	/**
	 * 根据工单查询点修记录
	 * @param eSRBean
	 * @param pageParams
	 * @return
	 */
	public DataGrid queryPlanInfo(EqmSpotchRecodeBean eSRBean,
			PageParams pageParams);
	/**
	 * 根据id 查询到具体的一条点修记录
	 * @param esrBean
	 * @return
	 */
	public DataGrid queryInfoById(String id);
}
