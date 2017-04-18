package com.shlanbao.tzsc.pms.cos.maintain.service;

import java.util.List;

import com.shlanbao.tzsc.base.mapping.CosMatPriceMaintain;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.cos.maintain.beans.MaintainBean;
import com.shlanbao.tzsc.pms.cos.maintain.beans.UnitBean;

public interface MaintainServiceI {
	/**
	 * 查询物料成本信息
	 * @param maintainBean
	 * @param pageParams
	 * @return
	 */
	public DataGrid queryCosMaintain(MaintainBean maintainBean,PageParams pageParams) throws Exception;
	
	/**
	 * 添加物料成本维护信息
	 * @param maintainBean
	 */
	public void addCosMaintain(MaintainBean maintainBean)  throws Exception;
	
	/**
	 * 编辑物料成本维护信息
	 * @param maintainBean
	 */
	public void editCosMaintain(MaintainBean maintainBean)  throws Exception;
	
	/**
	 * 根据id查询当前行数据
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public MaintainBean getMaintainBean(String id) throws Exception;
	/**
	 * 删除物料成本维护新信息
	 * @param id
	 */
	public void delCosMaintain(String id)  throws Exception;
	
	/**
	 * 获取所以单位
	 * @return
	 */
	public List<UnitBean> queryAllUtil() throws Exception;
	
	/**
	* @Title: getMaintainListByBean 
	* @Description: 根据Bean获取List 
	* @param  maintainBean
	* @return List<CosMatPriceMaintain>    返回类型 
	* @throws
	 */
	public List<CosMatPriceMaintain> getMaintainListByBean(MaintainBean maintainBean);
}
