package com.shlanbao.tzsc.pms.equ.wcplan.service;

import com.shlanbao.tzsc.base.mapping.EqmWheelCovelParam;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.pms.equ.wcplan.beans.EqmWheelCovelParamBean;

/**
 * 
* @ClassName: EqmWheelCovelParamServiceI 
* @Description: 保养详情 
* @author luo
* @date 2015年3月13日 下午3:02:11 
*
 */
public interface EqmWheelCovelParamServiceI{

	/**
	 * 
	* @Title: queryBeanList 
	* @Description: PMS界面中使用的查询  
	* @param  bean
	* @return DataGrid    返回类型 
	* @throws
	 */
	public DataGrid queryBeanList(EqmWheelCovelParamBean bean);
	/**
	 * 
	* @Title: updateBean 
	* @Description: 修改  
	* @param @param bean
	* @param @return    设定文件 
	* @return boolean    返回类型 
	* @throws
	 */
	public boolean updateBean(EqmWheelCovelParamBean bean);
	/**
	 * 
	* @Title: addWheelCovelParam 
	* @Description: 添加  
	* @param  bean 
	* @return boolean    返回类型 
	* @throws
	 */
	public boolean addWheelCovelParam(EqmWheelCovelParam bean);
	
	
	public void buildWheelCovelPlan();
}
