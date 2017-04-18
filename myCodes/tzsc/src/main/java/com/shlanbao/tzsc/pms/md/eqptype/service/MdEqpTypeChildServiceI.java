package com.shlanbao.tzsc.pms.md.eqptype.service;


import java.util.List;

import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.md.eqptype.beans.MdEqpTypeChildBean;


public interface MdEqpTypeChildServiceI {
	/**
	 * 查询设备类型
	 * @param mdTypeBean 设备类型bean
	 * @param pageParams
	 * @return 设备类型Grid
	 * @throws Exception
	 */
	public DataGrid queryMdTypeChild(MdEqpTypeChildBean mdTypeBean,PageParams pageParams) throws Exception;
	/**
	 * 新增 或 删除 绑定的 轮保大类项
	 * @param type
	 * @param beans
	 * @throws Exception
	 */
	public void saveMdTypeChild(String type,MdEqpTypeChildBean[] beans) throws Exception;
	/**
	 * 取消 绑定的 轮保大类项
	 * @param type
	 * @param beans
	 * @throws Exception
	 */
	public void editMdTypeChild(String type,MdEqpTypeChildBean[] beans) throws Exception;
	/**
	* @Title: getPaulbyEqpType 
	* @Description: 查询设备保养规则  
	* @param 设备类型Id
	* @param 保养规则
	* @return List<MdEqpTypeChildBean> 返回类型 
	* @throws
	 */
	public List<MdEqpTypeChildBean> getPaulbyEqpType(String eqpTypeId,String type);
	/**
	 * 根据设备型号ID查询对应的轮保规则
	 * @param equBean
	 * @param pageParams
	 * @return
	 */
	public List<MdEqpTypeChildBean> queryEqpTypeChildByEqp(String eqpid,String type)throws Exception;

	/**
	 *  根据设备ID和类型查询
	 * @param eqpId 设备ID
	 * @param type 类型包括lb,rh,dj
	 * @return
	 */
	public List<MdEqpTypeChildBean> getPaulbyEqp(String eqpId,String type);
}
