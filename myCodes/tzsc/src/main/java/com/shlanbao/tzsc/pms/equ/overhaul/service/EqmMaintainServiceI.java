package com.shlanbao.tzsc.pms.equ.overhaul.service;

import java.util.List;

import com.shlanbao.tzsc.base.mapping.EqmMaintain;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.equ.overhaul.beans.EqmMaintainBean;
import com.shlanbao.tzsc.pms.sys.datadict.beans.SysEqpTypeBean;

//设备检修项目维护
public interface EqmMaintainServiceI {
	/**
	 * 添加设备检修项目
	 * @param eqmMaintain
	 * @throws Exception
	 */
	public void addEqmMaintain(EqmMaintainBean eqmMaintain)throws Exception;
	
	/**
	 * 逻辑删除设备检修项目维护
	 * @param id
	 * @throws Exception
	 */
	public void deleteEqmMaintainById(String id)throws Exception;
	
	/**
	 * 分页查询设备检修项目
	 * @param eqmMaintain
	 * @param pageParams
	 * @return
	 * @throws Exception
	 */
	public DataGrid queryEqmMaintain(EqmMaintainBean eqmMaintain,PageParams pageParams)throws Exception;
	
	/**
	 * 通过ID查询设备检修
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public EqmMaintainBean getEqmMaintainById(String id)throws Exception;
	
	/**
	* @Title: editEqmMaintain 
	* @Description: 编辑  
	* @param eqmMaintain
	* @throws Exception
	 */
	public void editEqmMaintain(EqmMaintainBean eqmMaintain) throws Exception ;
	/**
	 * 导入excel设备检修历史
	 * @param list
	 * @throws Exception 
	 */
	public void inputExeclAndReadWrite(List<EqmMaintainBean> list) throws Exception;
	
	/**
	 * 更新备品配件的数量信息
	 */
	public void updateSpareParts(String ids,String use_num,String all_num);

	public Object[] loadToRhBuWeiCode(String equipmentId);

}
