package com.shlanbao.tzsc.pms.cos.spare.service;

import java.util.List;

import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.cos.spare.beans.CosSparePartsBean;

public interface CosSparePartsServiceI {

	/**
	* @Title: saveOrUpdateBean 
	* @Description:  添加或修改
	* @param bean
	* @return boolean
	* @throws Exception
	 */
	public boolean saveOrUpdateBean(CosSparePartsBean bean) throws Exception;
	/**
	* @Title: queryListByBeans 
	* @Description: 查询list
	* @param bean
	* @return List<CosSparePartsBean>
	* @throws Exception
	 */
	public List<CosSparePartsBean> queryListByBeans(CosSparePartsBean bean) throws Exception;
	/**
	* @Title: queryGridByBean 
	* @Description: easyui 界面查询方法 
	* @param bean
	* @param pageParams
	* @return DataGrid
	* @throws Exception
	 */
	public DataGrid queryGridByBean(CosSparePartsBean bean,PageParams pageParams) throws Exception;
	/**
	* @Title: getBeanById 
	* @Description: 根据id获取bean 
	* @param id
	* @return CosSparePartsBean
	* @throws Exception
	 */
	public CosSparePartsBean getBeanById(String id) throws Exception;
	/**
	 * 
	* @Title: deleteBean 
	* @Description: 根据id删除
	* @param id
	 */
	public void deleteBean(String id);
	/**
	 * 假删除
	 * @param id
	 * @return
	 */
	public boolean deleteById(String id);
	/**
	 * 
	* @Title:备品备件批量保存
	 */
	public boolean batchInputExeclAndReadWrite(List<CosSparePartsBean>  lt, CosSparePartsBean cspb) throws Exception;
	/**
	 * PMS查询备品备件
	 * 张璐
	 * @param bean
	 * @param pageParams
	 * @param length
	 * @param isNameFuzzy
	 * @return
	 * @throws Exception
	 */
	public List<CosSparePartsBean> queryListByBeansPMS(CosSparePartsBean bean,PageParams pageParams,long length,boolean isNameFuzzy) throws Exception;
	
	public DataGrid queryGridByBeanPMS(CosSparePartsBean bean,PageParams pageParams) throws Exception;
}
