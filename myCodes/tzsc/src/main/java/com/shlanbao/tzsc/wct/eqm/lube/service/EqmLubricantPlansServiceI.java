package com.shlanbao.tzsc.wct.eqm.lube.service;

import com.shlanbao.tzsc.base.mapping.EqmLubricantPlan;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.wct.eqm.lube.beans.EqmLubricantPlanBeans;

/**
* @ClassName: EqmLubricantPlanServiceI 
* @Description: 设备润滑计划 
* @author luo
* @date 2015年7月14日 上午11:36:30 
*
 */
public interface EqmLubricantPlansServiceI {
	/**
	 * 
	* @Title: saveBean 
	* @Description: 新增  
	* @param  bean
	* @return boolean    返回类型 
	* @throws
	 */
	public EqmLubricantPlan saveBean(EqmLubricantPlan bean);
	/**
	 * 
	* @Title: updateBean 
	* @Description: 修改  
	* @param  bean
	* @return boolean    返回类型 
	* @throws
	 */
	public boolean updateBean(EqmLubricantPlanBeans bean) throws Exception;

	/**
	 * 
	* @Title: queryDataGrid 
	* @Description: 查询  
	* @param  bean
	* @param  pageParams
	* @throws Exception    设定文件 
	* @return DataGrid    返回类型 
	* @throws
	 */
	public DataGrid queryDataGrid(EqmLubricantPlanBeans bean,PageParams pageParams) throws Exception;
	/**
	 * 
	* @Title: getBeanById 
	* @Description: 根据ID获取对象  
	* @param  id
	* @return EqmLubricantPlan    返回类型 
	* @throws
	 */
	public EqmLubricantPlan getBeanById(String id);
	
	/**
	 * 
	* @Title: getBeanById 
	* @Description: 根据ID获取对象  
	* @param  id
	* @return EqmLubricantPlan    返回类型 
	* @throws
	 */
	public EqmLubricantPlanBeans getBeanByIds(String id) throws Exception;
	
	
	/**
	* @Title: createLubriPlan 
	* @Description: 生成润滑计划  
	* @param lubri_id
	* @param eqp_id
	* @param eqp_code
	 */
	public void createLubriPlan(String lubri_id,String eqp_id,String eqp_code);
}
