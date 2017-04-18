package com.shlanbao.tzsc.pms.sch.constd.service;

import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.sch.constd.beans.ConStdBean;


/**
 * 标准单耗
 * @author Leejean
 * @create 2014年11月25日下午1:22:15
 */
public interface ConStdServiceI {
	/**
	 * 查询所有标准单耗(首页调用)
	 * @author Leejean
	 * @create 2014年11月26日下午5:42:08
	 * @return
	 * @throws Exception
	 */
	public DataGrid getConStds(ConStdBean ConStd,PageParams pageParams) throws Exception;
	/**
	 * 新增标准单耗
	 * @author Leejean
	 * @create 2014年11月26日下午4:28:56
	 * @param ConStd
	 * @throws Exception
	 */
	public void addConStd(ConStdBean ConStd) throws Exception;
	/**
	 * 编辑标准单耗
	 * @author Leejean
	 * @create 2014年11月26日下午4:29:04
	 * @param ConStd
	 * @throws Exception
	 */
	public void editConStd(ConStdBean ConStd) throws Exception;
	/**
	 * 删除标准单耗
	 * @author Leejean
	 * @create 2014年11月26日下午4:29:12
	 * @param id
	 * @throws Exception
	 */
	public void deleteConStd(String id) throws Exception;
	/**
	 * 根据ID获取标准单耗
	 * @author Leejean
	 * @create 2014年11月26日下午6:43:13
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	public ConStdBean getConStdById(String id) throws Exception;
	
	/**
	 * 查询所有标准单耗
	 * @param ConStd
	 * @return
	 * @throws Exception
	 */
	public DataGrid getAllConStds(ConStdBean ConStd) throws Exception;

}
