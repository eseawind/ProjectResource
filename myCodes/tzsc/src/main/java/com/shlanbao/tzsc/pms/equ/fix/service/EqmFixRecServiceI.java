package com.shlanbao.tzsc.pms.equ.fix.service;

import com.shlanbao.tzsc.base.mapping.EqmFixRec;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.equ.fix.beans.EqmFixRecBean;

/**
 * 设备维修记录服务接口
 * @author yangbo
 *
 */
public interface EqmFixRecServiceI{
	
	/**
	 * 添加或者修改设备维修记录
	 * @param eqmFixRec
	 * @throws Exception
	 */
	public void addFixRec(EqmFixRecBean eqmFixRecBean)throws Exception;
	
	/**
	 * 通过ID查询设备维修记录
	 * @param id
	 * @return 设备维修记录实体
	 * @throws Exception
	 */
	public EqmFixRecBean getFixRecById(String id)throws Exception;
	
	/**
	 * 分页查询设备维修记录
	 * @param eqmFixRec
	 * @param pageParams
	 * @return 设备维修记录表格分页数据
	 * @throws Exception
	 */
	public DataGrid queryFixRec(EqmFixRecBean eqmFixRecBean,PageParams pageParams)throws Exception; 
	
	/**
	 * 删除设备维修记录
	 * @param id
	 * @throws Exception
	 */
	public void deleteFixRec(String id)throws Exception;
}
