package com.shlanbao.tzsc.pms.md.matparam.service;

import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.pms.md.matparam.beans.MatParamBean;


/**
 * 辅料系数
 * @author Leejean
 * @create 2014年11月25日下午1:22:15
 */
public interface MatParamServiceI {
	/**
	 * 查询所有辅料系数
	 * @author Leejean
	 * @create 2014年11月26日下午5:42:08
	 * @return
	 * @throws Exception
	 */
	public DataGrid getAllMatParams(MatParamBean matParam) throws Exception;
	/**
	 * 新增辅料系数
	 * @author Leejean
	 * @create 2014年11月26日下午4:28:56
	 * @param matParam
	 * @throws Exception
	 */
	public void addMatParam(MatParamBean matParam) throws Exception;
	/**
	 * 编辑辅料系数
	 * @author Leejean
	 * @create 2014年11月26日下午4:29:04
	 * @param matParam
	 * @throws Exception
	 */
	public void editMatParam(MatParamBean matParam) throws Exception;
	/**
	 * 删除辅料系数
	 * @author Leejean
	 * @create 2014年11月26日下午4:29:12
	 * @param id
	 * @throws Exception
	 */
	public void deleteMatParam(String id) throws Exception;
	/**
	 * 根据ID获取辅料系数
	 * @author Leejean
	 * @create 2014年11月26日下午6:43:13
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	public MatParamBean getMatParamById(String id) throws Exception;

}
