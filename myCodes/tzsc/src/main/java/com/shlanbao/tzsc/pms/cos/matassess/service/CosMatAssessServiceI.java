package com.shlanbao.tzsc.pms.cos.matassess.service;

import java.util.List;

import com.shlanbao.tzsc.base.mapping.CosMatAssess;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.cos.matassess.bean.CosMatAssessBean;
/**
 * 
* @ClassName: CosMatAssessServiceI 
* @Description: 辅料考核版本控制Service
* @author luoliang
* @date 2015-1-7 上午10:42:12 
*
 */
public interface CosMatAssessServiceI {

	//添加或者修改Bean
	public boolean addOrUpdateBean(CosMatAssessBean bean) throws Exception;
	//根据ID获取Bean
	public CosMatAssessBean getBeanById(String id) throws Exception;
	//查询Bean
	public DataGrid queryBean(CosMatAssess bean,PageParams pageParams) throws Exception;
	//根据ID查mapping bean
	public CosMatAssess getBeanByIds(String id) throws Exception;
	
	public List<CosMatAssess> queryBeans(CosMatAssess bean, PageParams pageParams);
	
	public boolean editStatus(String id) throws Exception;
	//根据扩展Bean查询
	DataGrid queryBeanByExtends(CosMatAssessBean bean, PageParams pageParams)throws Exception;
}
