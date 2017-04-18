package com.shlanbao.tzsc.pms.equ.sbglplan.service;


import java.util.List;
import java.util.Map;

import com.shlanbao.tzsc.base.mapping.EqmWheelCovelParam;
import com.shlanbao.tzsc.base.mapping.EqmWheelCovelPlan;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.equ.sbglplan.beans.EqmPlanBean;
import com.shlanbao.tzsc.pms.equ.sbglplan.beans.EquipmentsBean;
import com.shlanbao.tzsc.pms.equ.wcplan.beans.BatchWCPlan;
import com.shlanbao.tzsc.pms.equ.wcplan.beans.EqmWheelCovelParamBean;
/**
 * 
* @ClassName: EqmPlanServiceI 
* @Description: 设备轮保管理 
*
 */
public interface EqmPlanServiceI {
	/** 
	 * 查询设备主数据
	 * @param equBean  设备主数据Bean对象
	 * @param pageParams 查询参数
	 * @return
	 */
	public DataGrid queryEqu(EquipmentsBean equBean,PageParams pageParams) throws Exception;
	/**
	 * 根据设备型号ID查询对应的轮保规则
	 * @return
	 */
	public DataGrid queryEqpTypeChild(EquipmentsBean equBean,PageParams pageParams)throws Exception;
	/**
	 * 设备轮保计划新增
	 * @param eqmResumeBean
	 * @param userId
	 * @throws Exception
	 */
	public EqmWheelCovelPlan addWCPlan(EqmPlanBean wcpBean, String userId) throws Exception;
	/**
	 * 添加计划对应的设备
	 * @param planBean
	 * @throws Exception
	 */
	public void addEqmWheelCovelParam(EqmWheelCovelPlan planBean,String ruleID) throws Exception;
	/**
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public EqmPlanBean getById(String id) throws Exception;
	/**
	 * 设备轮保计划修改
	 * @param eqmResumeBean
	 * @param userId
	 * @throws Exception
	 */
	public void editWCPlan(EqmPlanBean wcpBean, String userId) throws Exception;
	
	/**
	 * 根据主键ID 修改状态
	 * @param id		主键ID
	 * @param statusId	状态：0 新增 1.审核 ，2批准
	 * @param userId
	 */
	public void updateWCPlanStatus(String id, String statusId,String userId);
	/**
	 * 根据计划主键ID 查询维护项目
	 * @return
	 */
	public DataGrid queryBeanList(EqmWheelCovelParamBean bean)throws Exception;
	
	public void batchUpdateWCPlanStatus(String id, String statusId,String userId);
	
	/**
	 * 【功能说明】：设备轮保批量添加
	 * 
	 * */
	public String saveWcPlanf(EqmWheelCovelPlan ep);
	
	
	public List<?> queryEqpTypeChild(BatchWCPlan b);
	
	public Map<String,String> querySysEqpType(BatchWCPlan b);
	
	public void saveWcParam(EqmWheelCovelParam ecpb);
	public void deleteWCPlan(String id);
	public void edit(String id);
}
