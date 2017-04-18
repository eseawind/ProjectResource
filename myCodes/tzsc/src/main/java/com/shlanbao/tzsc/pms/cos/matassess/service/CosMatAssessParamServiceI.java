package com.shlanbao.tzsc.pms.cos.matassess.service;

import java.util.List;

import com.shlanbao.tzsc.base.mapping.CosMatAssessParam;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.cos.matassess.bean.CosMatAssessParamBean;
/**
 * 
* @ClassName: CosMatAssessServiceI 
* @Description: 辅料考核版本控制Service
* @author luoliang
* @date 2015-1-7 上午10:42:12 
*
 */
public interface CosMatAssessParamServiceI {

	//添加或者修改Bean
	public boolean addOrUpdateBean(CosMatAssessParamBean bean) throws Exception;
	//根据ID获取Bean
	public CosMatAssessParamBean getBeanById(String id) throws Exception;
	//查询Bean
	public DataGrid queryBean(CosMatAssessParam bean,PageParams pageParams) throws Exception;
	//根据ID查mapping bean
	public CosMatAssessParam getBeanByIds(String id) throws Exception;
	
	public List<CosMatAssessParam> queryBeans(CosMatAssessParam bean, PageParams pageParams);
	
	//根据扩展Bean查询
	public List<CosMatAssessParam> queryBeansByExpand(CosMatAssessParamBean bean,PageParams pageParams);
}
