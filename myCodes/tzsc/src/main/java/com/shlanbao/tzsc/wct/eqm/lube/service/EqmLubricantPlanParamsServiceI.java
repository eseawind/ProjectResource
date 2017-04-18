package com.shlanbao.tzsc.wct.eqm.lube.service;

import com.shlanbao.tzsc.base.mapping.EqmLubricantPlanParam;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.wct.eqm.lube.beans.EqmLubricantPlanParamBeans;
import com.shlanbao.tzsc.wct.sys.login.beans.LoginBean;

/**
* @ClassName: EqmLubricantPlanServiceParamI 
* @Description: 设备润滑计划详情 
* @author luo 
* @date 2015年7月15日 下午3:34:01 
*
 */
public interface EqmLubricantPlanParamsServiceI {

	/**
	* @Title: savePlanParam 
	* @Description: 保存  
	* @param bean
	* @return
	 */
	public boolean savePlanParam(EqmLubricantPlanParamBeans bean);
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
	public DataGrid queryDataGrid(EqmLubricantPlanParamBeans bean,PageParams pageParams) throws Exception;
	/**
	* @Title: getBeanByIds 
	* @Description: 根据ID获取详细  
	* @param id
	* @return
	 */
	public EqmLubricantPlanParamBeans getBeanByIds(String id) throws Exception;
	/**
	* @Title: updateBean 
	* @Description: 修改明细  
	* @param bean
	* @return
	* @throws Exception
	 */
	public boolean updateBean(EqmLubricantPlanParamBeans bean) throws Exception;
	/**
	* @Title: queryDataGridByPlanId 
	* @Description: 根据润滑计划ID获取详细润滑内容  
	* @param id
	* @return
	* @throws Exception
	 */
	public DataGrid queryDataGridByPlanId(String id,String type) throws Exception;
	/**
	* @Title: lubricantParamEnd 
	* @Description: WCT完成润滑项  
	* @param bean
	* @param login
	* @return
	 */
	public boolean lubricantParamEnd(EqmLubricantPlanParamBeans[] bean,LoginBean login);
	
	public EqmLubricantPlanParamBeans getTypeById(String id) throws Exception;
	
	/**
	 * pointInfo查询
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public EqmLubricantPlanParamBeans piontInfo(String id) throws Exception;
	
	/**
	 * ZF部位查询
	 * @param rhpart
	 * @return
	 * @throws Exception
	 */
	public DataGrid findZF(String rhpart) throws Exception;
}
