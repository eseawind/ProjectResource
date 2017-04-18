package com.shlanbao.tzsc.pms.equ.lubricate.service;

import java.util.List;

import com.shlanbao.tzsc.base.mapping.EqmLubricantPlan;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.equ.lubricate.beans.EqmBubricantfBean;
import com.shlanbao.tzsc.pms.equ.lubricate.beans.EqmLubricantPlanBean;

/**
* @ClassName: EqmLubricantPlanServiceI 
* @Description: 设备润滑计划 
* @author luo
* @date 2015年7月14日 上午11:36:30 
*
 */
public interface EqmLubricantPlanServiceI {
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
	public boolean updateBean(EqmLubricantPlanBean bean) throws Exception;

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
	public DataGrid queryDataGrid(EqmLubricantPlanBean bean,PageParams pageParams) throws Exception;
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
	public EqmLubricantPlanBean getBeanByIds(String id) throws Exception;
	
	/**
	 * 功能说明：设备润滑计划管理-添加
	 * @author wanchanghuang
	 * @time 2015年8月21日16:35:42
	 * 
	 * */
	public List<?> addEqmLubricatPlan(EqmBubricantfBean bean);
	public void deleteCycle(String ids) throws Exception;
	
}
