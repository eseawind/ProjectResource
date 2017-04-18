package com.shlanbao.tzsc.pms.equ.wcplan.service;

import java.util.List;

import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.equ.wcplan.beans.BatchWCPlan;
import com.shlanbao.tzsc.pms.equ.wcplan.beans.EqmWheelCalendar;
import com.shlanbao.tzsc.pms.equ.wcplan.beans.EqmWheelCovelPlanBean;
/**
 * 
* @ClassName: EqmWheelCovelPlanServiceI 
* @Description: 设备轮保管理 
* @author luo
* @date 2015年3月12日 下午5:08:03 
*
 */
public interface EqmWheelCovelPlanServiceI {

	/**
	 * 设备轮保计划查询
	 * @param wcpBean
	 * @param pageParams
	 * @return DataGrid
	 * @throws Exception
	 */
	public DataGrid queryWCPlan(EqmWheelCovelPlanBean wcpBean,PageParams pageParams) throws Exception;
	/**
	 * 
	* @Title: updateWCPlanStatus 
	* @Description: 根据ID修改状态  
	* @param  id
	* @param  status    设定文件 
	* @throws
	 */
	public void updateWCPlanStatus(String id,String status);
	
	/**
	 * 设备轮保计划新增
	 * @param eqmResumeBean
	 * @param userId
	 * @throws Exception
	 */
	public void addWCPlan(EqmWheelCovelPlanBean wcpBean,String userId) throws Exception;
	/**
	 * 设备轮保计划编辑
	 * @param wcpBean
	 * @throws Exception
	 */
	public void editWCPlan(EqmWheelCovelPlanBean wcpBean) throws Exception;
	
	/**
	 * 获取当前行数据
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public EqmWheelCovelPlanBean getById(String id) throws Exception;
	
	/**
	 * 删除轮保计划
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public void deleteWCPlan(String id) throws Exception;
	
	/**
	* @Title: queryWCPlanCalendar 
	* @Description: 根据日期获取DataGrid  
	* @param @param date1
	* @param @param date2
	* @param @return
	* @param @throws Exception    设定文件 
	* @return DataGrid    返回类型 
	* @throws
	 */
	public List<EqmWheelCalendar> queryWCPlanCalendar(String date1,String date2) throws Exception ;
	
	
}
