package com.shlanbao.tzsc.pms.equ.lubricate.service;

import java.util.List;

import com.shlanbao.tzsc.base.mapping.EqmLubricantMaintain;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.equ.lubricate.beans.EquLubricantMaintainBean;

/**
 * 设备润滑管理接口
 * @author liuligong
 *
 */
public interface EqmLubricantMaintainServiceI {
	/**
	 * @TODO 添加润滑管理
	 * @param object
	 * @throws Exception
	 */
	public void addLubricate(EqmLubricantMaintain equLubricantMaintain)throws Exception;
	
	/**
	 * @TODO 查询设备润滑管理列表
	 * @param object
	 * @param pageParams
	 * @return
	 * @throws Exception
	 */
	public DataGrid queryLubricate(EqmLubricantMaintain equLubricantMaintain,PageParams pageParams)throws Exception;
	
	/**
	 * 获取所有的润滑剂
	 * @return
	 * @throws Exception
	 */
	public List<EquLubricantMaintainBean> queryAllLubricant(String type)throws Exception;
	
	
	/**
	 * 根据ID 查询其下的所有子类
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public List<EquLubricantMaintainBean> queryListById(String key,String type) throws Exception;
	
	/**
	 * 根据ID获取设备润滑管理
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Object getLubricateById(String id)throws Exception;
	
	/**
	 * 根据ID删除设备润滑管理
	 * @param id
	 * @throws Exception
	 */
	public void deleteLuricateById(String id)throws Exception;
}
