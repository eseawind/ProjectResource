package com.shlanbao.tzsc.pms.equ.lubricate.service;

import java.util.List;

import com.shlanbao.tzsc.base.mapping.EqmLubricant;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.equ.lubricate.beans.EqmLubricantBean;
/**
* @ClassName: EqmLubricantServiceI 
* @Description: 润滑计划管理 
* @author luo
* @date 2015年7月8日 下午3:18:20 
*
 */
public interface EqmLubricantServiceI {
	/**
	* @Title: addBean 
	* @Description: 新增  
	* @param  bean
	* @return boolean    返回类型 
	* @throws
	 */
	public boolean addBean(EqmLubricantBean bean);
	/**
	* @Title: updateBean 
	* @Description: 修改  
	* @param  bean
	* @return boolean    返回类型 
	* @throws
	 */
	public boolean updateBean(EqmLubricantBean bean);
	/**
	* @Title: searchBean 
	* @Description: 查询  
	* @param  bean
	* @return DataGrid    返回类型 
	* @throws
	 */
	public DataGrid searchBean(EqmLubricantBean bean,PageParams pageParams);
	/**
	* @Title: getBeanById 
	* @Description: 根据Id查询  
	* @param  id
	* @return EqmLubricant    返回类型 
	* @throws
	 */
	public EqmLubricant getBeanById(String id);
	/**
	* @Title: getBeanByIds 
	* @Description: 根据Id查询  
	* @param  id
	* @return EqmLubricantBean    返回类型 
	* @throws
	 */
	public EqmLubricantBean getBeanByIds(String id);
	
	public List<?> getListBySql(String sql);
}
