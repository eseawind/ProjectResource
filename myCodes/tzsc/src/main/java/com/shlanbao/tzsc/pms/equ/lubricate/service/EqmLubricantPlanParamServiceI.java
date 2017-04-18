package com.shlanbao.tzsc.pms.equ.lubricate.service;

import com.shlanbao.tzsc.base.mapping.EqmLubricantPlanParam;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.equ.lubricate.beans.EqmLubricantPlanParamBean;

/**
* @ClassName: EqmLubricantPlanServiceParamI 
* @Description: 设备润滑计划详情 
* @author luo
* @date 2015年7月15日 下午3:34:01 
*
 */
public interface EqmLubricantPlanParamServiceI {

	/**
	* @Title: savePlanParam 
	* @Description: 保存  
	* @param bean
	* @return
	 */
	public boolean savePlanParam(EqmLubricantPlanParamBean bean);
	/**
	* @Title: savePlanParams 
	* @Description: 保存  
	* @param bean
	* @return
	 */
	public boolean savePlanParams(EqmLubricantPlanParam bean);
	/**
	* @Title: queryDataGrid 
	* @Description: 查询设备润滑详细项  
	* @param bean
	* @param pageParams
	* @return
	* @throws Exception
	 */
	public DataGrid queryDataGrid(EqmLubricantPlanParamBean bean,PageParams pageParams) throws Exception;
	/**
	* @Title: getBeanByIds 
	* @Description: 根据ID获取详细  
	* @param id
	* @return
	 */
	public EqmLubricantPlanParamBean getBeanByIds(String id) throws Exception;
	/**
	* @Title: updateBean 
	* @Description: 修改明细  
	* @param bean
	* @return
	* @throws Exception
	 */
	public boolean updateBean(EqmLubricantPlanParamBean bean) throws Exception;
	/**
	* @Title: queryDataGridByPlanId 
	* @Description: 根据润滑计划ID获取详细润滑内容  
	* @param id
	* @return
	* @throws Exception
	 */
	public DataGrid queryDataGridByPlanId(String id) throws Exception;
}
