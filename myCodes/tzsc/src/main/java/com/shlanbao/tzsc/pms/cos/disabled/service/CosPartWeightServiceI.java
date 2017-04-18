package com.shlanbao.tzsc.pms.cos.disabled.service;

import java.util.List;

import com.shlanbao.tzsc.base.mapping.CosPartWeight;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.cos.disabled.bean.CosPartWeightBean;

/**
 * 
* @ClassName: CosPartWeightServiceI 
* @Description: 烟支重量维护
* @author luoliang
* @date 2015-1-5 上午11:12:29 
*
 */
public interface CosPartWeightServiceI {
	
	/**
	 * 
	* @Title: queryCosDisabled 
	* @Description: 烟支重量查询 
	* @param @param mdTypeBean
	* @param @param pageParams
	* @param @throws Exception   
	* @return DataGrid 返回类型 
	* @throws
	 */
	public DataGrid queryCosPartWeight(CosPartWeightBean bean, PageParams pageParams) throws Exception;
	
	
	/**
	 * 
	* @Title: addOrUpdateBean 
	* @Description: 烟支重量添加或修改
	* @param @param bean
	* @param @throws Exception     
	* @return boolean    返回类型 
	* @throws
	 */
	public boolean addOrUpdateBean(CosPartWeight bean)throws Exception;
	
	/**
	 * 
	* @Title: getBeanById 
	* @Description:  根据ID获取Bean
	* @param @param id 
	* @param @throws Exception   
	* @return CosIncStandardBean    返回类型 
	* @throws
	 */
	public CosPartWeight getBeanById(String id) throws Exception;
	
	/**
	* @Title: getBeanByPartNumber 
	* @Description: 根据ID和牌号ID获取bean 
	* @param  id
	* @param  partNumber
	* @throws Exception    设定文件 
	* @return CosPartWeight    返回类型 
	 */
	public CosPartWeight getBeanByPartNumber(String id,String partNumber) throws Exception;
	
	/**
	* @Title: getBeanByPartNumber 
	* @Description: 和牌号ID获取List 
	* @param  partNumber
	* @throws Exception    设定文件 
	* @return CosPartWeight    返回类型 
	 */
	public List<CosPartWeight> getBeanByPartNumber(String partNumber) throws Exception;
}
