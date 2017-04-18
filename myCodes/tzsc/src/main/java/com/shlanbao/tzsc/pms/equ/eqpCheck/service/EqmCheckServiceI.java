package com.shlanbao.tzsc.pms.equ.eqpCheck.service;

import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.equ.eqpCheck.bean.EqmCheckPlanBean;

public interface EqmCheckServiceI {
	/**
	 * 批量添加点检计划
	 * @param bean
	 */
public void batchAdd(EqmCheckPlanBean bean) throws Exception;

/**
 * 查询
 * 2015.9.16--张璐
 * @param checkBean
 * @param pageParams
 * @return
 * @throws Exception
 */
public DataGrid queryEqmCheckPlan(EqmCheckPlanBean checkBean,PageParams pageParams) throws Exception;
/**
 * 批量删除
 * 2015.9.16--张璐
 * @param ids
 * @throws Exception
 */
public void batchDeleteEqmCheckPlan(String ids) throws Exception;
/**
 * 查询二级点检详细
 * @param pid
 * @return
 */
public DataGrid queryEqmCheckPlanParam(String pid) throws Exception;

}
