package com.shlanbao.tzsc.pms.sys.log.service;

import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.sys.log.beans.LogBean;
/**
 * 日志操作接口
 * <li>@author Leejean
 * <li>@create 2014-7-5下午05:45:07
 */
public interface LogServiceI {
	/**
	 * 保存日志
	 * @author Leejean
	 * @create 2014年9月4日上午11:43:59
	 * @param logBean
	 */
	public void saveLog(LogBean logBean) throws Exception;
	/**
	 * 查询日志
	 * @author Leejean
	 * @create 2014年9月4日上午11:44:09
	 * @param logBean
	 * @param pageParams
	 * @return
	 */
	public DataGrid getAllLogs(LogBean logBean, PageParams pageParams)throws Exception;
	/**
	 * 批量删除日志
	 * @author Leejean
	 * @create 2014年9月16日下午12:59:30
	 * @param ids
	 */
	public void batchDeleteLogs(String ids) throws Exception;;
	/**
	 * 删除日志
	 * @author Leejean
	 * @create 2014年9月16日下午12:59:42
	 * @param id
	 */
	public void deleteLog(String id) throws Exception;;
}
