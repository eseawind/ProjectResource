package com.shlanbao.tzsc.pms.equ.trouble.service;

import java.util.List;

import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.equ.trouble.beans.EqmTroubleBean;
import com.shlanbao.tzsc.pms.equ.trouble.beans.EqmTroubleInfoBean;
import com.shlanbao.tzsc.pms.equ.trouble.beans.TroubleBean;

public interface TroubleServiceI {
	/**
	 * 设备故障统计查询
	 * @param troubleBean
	 * @param pageParams
	 * @throws Exception
	 */
	public DataGrid queryTrouble(TroubleBean troubleBean,PageParams pageParams) throws Exception;
	
	
	/**
	 * @param eqmtroubleBean
	 * @param pageParams
	 * @return 
	 * @throws Exception
	 */
	public DataGrid queryTrouble(EqmTroubleBean eqmtroubleBean,PageParams pageParams) throws Exception;
	/**
	 * 张璐
	 * 设备信息表查询--测试用
	 * @return
	 */
	public List<EqmTroubleInfoBean> queryTroubleInfo(EqmTroubleInfoBean troubleBean);
	
	/**张璐2015-10-30
	 * 用于添加新的故障信息
	 * @param des--故障描述
	 * @param fiveCode--本级代码
	 * @param pid--父节点
	 */
	public void addNewTrouble(String des,String fiveCode,String pid,String id);
	
	/**
	 * 删除新添加的故障
	 * 张璐
	 */
	public void deleteNewTrouble(String id);
	
	
}
