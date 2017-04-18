package com.shlanbao.tzsc.pms.cos.param.service;

import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.cos.param.beans.CosMatParamBean;
import com.shlanbao.tzsc.pms.md.eqptype.beans.MdEqpTypeBean;


public interface CosMatParamServiceI {
	
	/**
	 * 奖罚单价新增或者修改
	 * @param bean
	 */
	public void addCosMat(CosMatParamBean bean) throws Exception;

	/**
	 * 查询设备型号
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public DataGrid queryCosMat(CosMatParamBean bean, PageParams pageParams) throws Exception;
	
	/**
	 * 查询设备型号
	 * @param bean
	 * @param pageParams
	 * @return
	 * @throws Exception
	 */
	public DataGrid queryMdType(MdEqpTypeBean mdTypeBean, PageParams pageParams) throws Exception;
	
	/**
	 * 根据ID获取当前辅料
	 * @param id
	 * @return
	 */
	public CosMatParamBean getCosMatById(String id)  throws Exception ;
	
	/**
	 * 编辑辅料奖罚金额
	 * @param wcpBean
	 * @throws Exception
	 */
	public void editCosMat(CosMatParamBean cosMatParamBean) throws Exception;
	
	
	/**
	 * 删除辅料奖罚金额
	 * @param id
	 * @throws Exception
	 */
	public void deleteCosMat(String id) throws Exception;
	
}
