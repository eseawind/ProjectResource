package com.shlanbao.tzsc.pms.cos.incomplete.service;

import java.util.List;

import com.shlanbao.tzsc.base.mapping.CosIncompleteCoefficient;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;

/**
 * @ClassName: IncompleteCoefficientServiceI 
 * @Description: 残烟丝考核系数设置
 * @author luo
 * @date 2015年1月22日 下午1:58:30 
 *
 */
public interface IncompleteCoefficientServiceI {
	/**
	* @Title: queryBeanById 
	* @Description: 根据ID获取bean
	* @param  id
	* @return CosIncompleteCoefficient    返回类型 
	* @throws
	 */
	public CosIncompleteCoefficient queryBeanById(String id);
	
	/**
	* @Title: queryBeanListByBean 
	* @Description: 根据Bean获取List集合
	* @param @param bean
	* @param @return    设定文件 
	* @return List<CosIncompleteCoefficient>    返回类型 
	* @throws
	 */
	public List<CosIncompleteCoefficient> queryBeanListByBean(CosIncompleteCoefficient bean);
	
	/**
	* @Title: queryBeanGridByBean 
	* @Description: 根据bean获取DataGrid 
	* @param  bean
	* @return DataGrid    返回类型 
	* @throws
	 */
	public DataGrid queryBeanGridByBean(CosIncompleteCoefficient bean,PageParams params);
	
	/**
	* @Title: queryBeanListByBean 
	* @Description: 添加或修改
	* @param  bean
	* @return boolean    返回类型 
	* @throws
	 */
	public boolean addOrUpdateBean(CosIncompleteCoefficient bean);
}
