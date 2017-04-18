package com.shlanbao.tzsc.pms.cos.disabled.service;

import java.util.List;

import com.shlanbao.tzsc.base.mapping.CosIncompleteStandard;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.cos.disabled.bean.CosIncStandardBean;

/**
* @ClassName: CosIncompleteStandardServiceI 
* @Description: 机台对应班次储烟量维护 
* @author luoliang
* @date 2014-12-31 下午04:38:29 
*
 */
public interface CosIncompleteStandardServiceI {
	
	/**
	 * 
	* @Title: queryCosDisabled 
	* @Description: 机台对应班次储烟量查询 
	* @param @param mdTypeBean
	* @param @param pageParams
	* @param @throws Exception   
	* @return DataGrid 返回类型 
	* @throws
	 */
	public DataGrid queryCosDisabled(CosIncompleteStandard bean, PageParams pageParams) throws Exception;
	
	
	/**
	 * 
	* @Title: addOrUpdateBean 
	* @Description: 机台对应班次储烟量添加或修改
	* @param @param bean
	* @param @throws Exception     
	* @return boolean    返回类型 
	* @throws
	 */
	public boolean addOrUpdateBean(CosIncompleteStandard bean)throws Exception;
	
	/**
	 * 
	* @Title: getBeanById 
	* @Description:  根据ID获取Bean
	* @param @param id 
	* @param @throws Exception   
	* @return CosIncStandardBean    返回类型 
	* @throws
	 */
	public CosIncStandardBean getBeanById(String id) throws Exception;
	/**
	* @Title: queryCosDisabled 
	* @Description: 根据bean获取集合 
	* @param  bean
	* @param @throws Exception    设定文件 
	* @return List<CosIncompleteStandard>    返回类型 
	* @throws
	 */
	public List<CosIncompleteStandard> queryCosDisabled(CosIncompleteStandard bean) throws Exception;
	
}
